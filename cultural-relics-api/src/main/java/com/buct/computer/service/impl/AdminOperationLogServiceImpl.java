package com.buct.computer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct.computer.common.exception.InvalidParamException;
import com.buct.computer.mapper.AdminOperationLogMapper;
import com.buct.computer.model.AdminOperationLog;
import com.buct.computer.model.UserInfo;
import com.buct.computer.service.IAdminOperationLogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuechenlong
 * @date 2022/5/2
 * @apiNote 管理员操作记录表 服务实现类
 */
@Service
public class AdminOperationLogServiceImpl extends ServiceImpl<AdminOperationLogMapper, AdminOperationLog> implements IAdminOperationLogService {

    private final String dictionary = "/root/backup/";
    private final File folder = new File(dictionary);
    private final Gson gson = new Gson();


    @Override
    public Map<String, String> getBackupFiles() {
        Map<String, String> backupMap = new HashMap<>();
        for (File file : folder.listFiles()) {
            backupMap.put(file.getName(), inputText(dictionary + file.getName()));
        }
        return backupMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String backup() {
        List<AdminOperationLog> adminOperationLogList = list();
        String adminOperationLogJsonString = gson.toJson(adminOperationLogList);
        LocalDateTime localDateTime = LocalDateTime.now();
        outputFile(dictionary + localDateTime, adminOperationLogJsonString);
        return localDateTime.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recoverFromBackup(String name) {
        String adminOperationLogJsonString = inputText(dictionary + name);
        if (StringUtils.isBlank(adminOperationLogJsonString)) {
            throw new RuntimeException("backup named " + name + " is empty");
        }
        List<AdminOperationLog> adminOperationLogList = gson.fromJson(adminOperationLogJsonString, new TypeToken<List<AdminOperationLog>>(){}.getType());
        Set<Long> idSet = adminOperationLogList.stream().map(AdminOperationLog::getId).collect(Collectors.toSet());
        remove(new QueryWrapper<AdminOperationLog>().notIn("id", idSet));
        saveOrUpdateBatch(adminOperationLogList);
    }

    private void outputFile(String path, String result){
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            OutputStream output = new FileOutputStream(path);
            output.write(result.getBytes());
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String inputText(String path){
        byte[] data = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        try {
            InputStream input = new FileInputStream(path);
            while ((c = input.read()) != -1) {
                stringBuilder.append((char)c);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    
}
