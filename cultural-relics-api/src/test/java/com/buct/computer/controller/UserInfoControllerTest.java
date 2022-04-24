package com.buct.computer.controller;

import com.buct.computer.model.UserBrowseLog;
import com.buct.computer.service.IUserBrowseLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/24/19:07
 */
@SpringBootTest
public class UserInfoControllerTest {

    @Autowired
    private IUserBrowseLogService userBrowseLogService;


    @Test
    void makeLog() throws ParseException {
        List<UserBrowseLog> userBrowseLogs = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            UserBrowseLog browseLog = UserBrowseLog.builder()
                    .userId(1)
                    .culturalRelicId(random.nextLong() % 3 + 1)
                    .browseTime(new SimpleDateFormat("yyyy-MM-dd").parse("2022-4-21"))
                    .build();
            userBrowseLogs.add(browseLog);
        }
        userBrowseLogService.saveBatch(userBrowseLogs);
    }

}
