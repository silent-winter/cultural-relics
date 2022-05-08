package com.buct.computer.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.computer.common.assembler.UserInfoAssembler;
import com.buct.computer.common.enums.UserTypeEnum;
import com.buct.computer.model.AdminOperationLog;
import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.model.UserInfo;
import com.buct.computer.request.UserLoginDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.UserInfoDetailVO;
import com.buct.computer.service.IAdminOperationLogService;
import com.buct.computer.service.ICulturalRelicCommentService;
import com.buct.computer.service.ICulturalRelicInfoService;
import com.buct.computer.service.IUserInfoService;
import lombok.AllArgsConstructor;
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

    @GetMapping("/info")
    public ApiResult<UserInfoDetailVO> getUserInfo(@RequestParam(value = "userId", required = false) Integer userId) {
        if (Objects.isNull(userId)) {
            // userId为空，默认查询当前登录用户的信息
            userId = StpUtil.getLoginId(0);
        }
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        UserInfoDetailVO userInfoDetailVO = UserInfoAssembler.MAPPER.userInfoToUserInfoDetailVO(userInfo);
        return ApiResult.success(userInfoDetailVO);
    }

    @GetMapping("/user/info")
    public ApiResult<List<UserInfo>> getAllUserInfo() {
        List<UserInfo> allUserInfo = userInfoService.list();
        return ApiResult.success(allUserInfo);
    }

    @PatchMapping("/user/info")
    public ApiResult<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        if (Objects.isNull(userInfo.getId())) {
            // userId为空，则默认当前登录用户的信息
            userInfo.setId(StpUtil.getLoginId(0));
        }
        if (userInfoService.updateById(userInfo)) {
            saveAdminOperationLog("update", "user(id=" + userInfo.getId() + ") info has been updated by admin(id=" + StpUtil.getLoginId(0) + ")");
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

    @AllArgsConstructor
    class BackupFile {
        private String name;
        private String text;
    }
    @GetMapping("/backup/files")
    public ApiResult<List<BackupFile>> getBackupFiles() {
        Map<String, String> backupMap = adminOperationLogService.getBackupFiles();
        List<BackupFile> backupFileList = new ArrayList<>();
        for (Map.Entry<String, String> entry : backupMap.entrySet()) {
            backupFileList.add(new BackupFile(entry.getKey(), entry.getValue()));
        }
        return ApiResult.success(backupFileList);
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