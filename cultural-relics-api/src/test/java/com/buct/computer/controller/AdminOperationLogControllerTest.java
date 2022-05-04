package com.buct.computer.controller;

import com.buct.computer.model.AdminOperationLog;
import com.buct.computer.service.IAdminOperationLogService;
import com.buct.computer.service.IUserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuechenlong
 * @date 2022/5/2
 * @apiNote
 */
@SpringBootTest
public class AdminOperationLogControllerTest {

    @Autowired
    private IAdminOperationLogService adminOperationLogService;

    @Test
    void exportData() {
        //adminOperationLogService.recoverFromBackup("2022-05-03T13:35:50.020");
        //adminOperationLogService.getBackupFiles();
        AdminOperationLog adminOperationLog = new AdminOperationLog();
        adminOperationLog.setType("123");
        adminOperationLog.setContent("test");
        adminOperationLogService.save(adminOperationLog);
        System.out.println("id:" + adminOperationLog.getId());
    }

}
