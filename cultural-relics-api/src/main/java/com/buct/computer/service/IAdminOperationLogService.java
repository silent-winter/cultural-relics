package com.buct.computer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buct.computer.model.AdminOperationLog;

import java.util.List;
import java.util.Map;

/**
 * @author yuechenlong
 * @date 2022/5/2
 * @apiNote 管理员操作记录表 服务类
 */
public interface IAdminOperationLogService extends IService<AdminOperationLog> {

    Map<String, String> getBackupFiles();

    String backup();

    void recoverFromBackup(String name);

}
