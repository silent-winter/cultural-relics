package com.buct.computer.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.computer.common.enums.UserTypeEnum;
import com.buct.computer.model.AdminOperationLog;
import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.model.UserInfo;
import com.buct.computer.request.UserLoginDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.IAdminOperationLogService;
import com.buct.computer.service.ICulturalRelicCommentService;
import com.buct.computer.service.ICulturalRelicInfoService;
import com.buct.computer.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author yuechenlong
 * @date 2022/5/1
 * @apiNote
 */
@ApiIgnore
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUserInfoService userInfoService;
    
    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;
    
    @Autowired
    private ICulturalRelicCommentService culturalRelicCommentService;

    @Autowired
    private IAdminOperationLogService adminOperationLogService;

    @PostMapping("/login")
    public ApiResult<SaTokenInfo> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserInfo savedAccount = userInfoService.getOne(new QueryWrapper<UserInfo>()
                .eq("user_name", userLoginDTO.getUserName())
                .eq("password", userLoginDTO.getPassword()));
        if (savedAccount == null || !UserTypeEnum.admin.getTypeName().equals(savedAccount.getType())) {
            return ApiResult.fail("the admin hasn't registered");
        }
        if (savedAccount.getStatus() == 0) {
            return ApiResult.fail("the user's permission has been closed");
        }
        StpUtil.login(savedAccount.getId());
        saveAdminOperationLog("login", "admin(id=" + savedAccount.getId() + ") login");
        return ApiResult.success(StpUtil.getTokenInfo());
    }

    @GetMapping("/logout")
    public ApiResult<String> logout() {
        StpUtil.logout();
        return ApiResult.success("logout successfully");
    }

    @GetMapping("/user/info")
    public ApiResult<List<UserInfo>> getAllUserInfo() {
        List<UserInfo> allUserInfo = userInfoService.list();
        return ApiResult.success(allUserInfo);
    }

    @PatchMapping("/user/info")
    public ApiResult<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        if (userInfoService.updateById(userInfo)) {
            saveAdminOperationLog("update", userInfo + " has been updated by admin(id=" + StpUtil.getLoginId(0) + ")");
            return ApiResult.success("update user info successfully");
        }
        return ApiResult.fail("fail to update user info");
    }

    @GetMapping("/relic")
    public ApiResult<List<CulturalRelicInfo>> getRelicList() {
        List<CulturalRelicInfo> culturalRelicInfoList = culturalRelicInfoService.list();
        return ApiResult.success(culturalRelicInfoList);
    }

    @PostMapping("/relic")
    public ApiResult<String> insertRelic(@RequestBody CulturalRelicInfo culturalRelicInfo) {
        culturalRelicInfoService.save(culturalRelicInfo);
        saveAdminOperationLog("insert", "admin(id=" + StpUtil.getLoginId(0) + ") has inserted the relic(id=" + culturalRelicInfo.getId() + ")");
        return ApiResult.success("insert relic successfully");
    }

    @PatchMapping("/relic")
    public ApiResult<String> updateRelic(@RequestBody CulturalRelicInfo culturalRelicInfo) {
        culturalRelicInfoService.updateById(culturalRelicInfo);
        saveAdminOperationLog("update", "admin(id=" + StpUtil.getLoginId(0) + ") has updated the relic(id=" + culturalRelicInfo.getId() + ")");
        return ApiResult.success("update relic(id=" + culturalRelicInfo.getId() + ") successfully");
    }

    @DeleteMapping("/relic/{ids}")
    public ApiResult<String> deleteRelic(@PathVariable("ids") String ids) {
        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
        culturalRelicInfoService.removeByIds(idList);
        saveAdminOperationLog("delete", "admin(id=" + StpUtil.getLoginId(0) + ") has deleted the relic(id=" + ids + ")");
        return ApiResult.success("delete relic(id=" + ids + ") successfully");
    }

    @GetMapping("/comment")
    public ApiResult<List<CulturalRelicComment>> getCommentList() {
        List<CulturalRelicComment> culturalRelicCommentList = culturalRelicCommentService.list();
        return ApiResult.success(culturalRelicCommentList);
    }

    @DeleteMapping("/comment/{ids}")
    public ApiResult<String> deleteComment(@PathVariable("ids") String ids) {
        List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
        culturalRelicCommentService.removeByIds(idList);
        saveAdminOperationLog("delete", "admin(id=" + StpUtil.getLoginId(0) + ") has deleted the comment(id=" + ids + ")");
        return ApiResult.success("delete comment(id=" + ids + ") successfully");
    }

    @GetMapping("/backup/files")
    public ApiResult<Map<String, String>> getBackupFiles() {
        Map<String, String> backupMap = adminOperationLogService.getBackupFiles();
        return ApiResult.success(backupMap);
    }

    @GetMapping("/backup")
    public ApiResult<String> backup() {
        String backupFileName = adminOperationLogService.backup();
        saveAdminOperationLog("backup", "admin(id=" + StpUtil.getLoginId(0) +") backup the database, backup file: " + backupFileName);
        return ApiResult.success(backupFileName);
    }

    @PutMapping("/recover/{name}")
    public ApiResult<String> recover(@PathVariable("name") String name) {
        adminOperationLogService.recoverFromBackup(name);
        saveAdminOperationLog("recover", "admin(id=" + StpUtil.getLoginId(0) +") recover the database by backup file: " + name);
        return ApiResult.success("database has recovered to " + name);
    }

    @GetMapping("/operation/log")
    public ApiResult<List<AdminOperationLog>> getAllAdminOperationLog() {
        List<AdminOperationLog> allAdminOperationLog = adminOperationLogService.list();
        return ApiResult.success(allAdminOperationLog);
    }

    private void saveAdminOperationLog(String type, String content) {
        AdminOperationLog adminOperationLog = new AdminOperationLog();
        adminOperationLog.setType(type);
        adminOperationLog.setContent(content);
        adminOperationLogService.save(adminOperationLog);
    }

}