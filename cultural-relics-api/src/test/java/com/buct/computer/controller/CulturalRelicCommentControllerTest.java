package com.buct.computer.controller;

import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.service.ICulturalRelicCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/23/13:45
 */
@SpringBootTest
public class CulturalRelicCommentControllerTest {

    @Autowired
    private ICulturalRelicCommentService culturalRelicCommentService;


    @Test
    void testCommentList() {

    }

    @Test
    void makeParentComment() {
        Random random = new Random();
        List<CulturalRelicComment> result = new ArrayList<>();
        for (int i = 0; i <= 200; i++) {
            int userId = random.nextInt(10);
            CulturalRelicComment comment = CulturalRelicComment.builder()
                    .culturalRelicId((long) (i % 3 + 1))
                    .content("我是一条评论" + i)
                    .likeNum(random.nextInt(100))
                    .publishUserId(userId)
                    .publishUserName("userName" + userId)
                    .status(1)
                    .parentCommentId(null)
                    .build();
            result.add(comment);
        }
        culturalRelicCommentService.saveBatch(result);
    }

    @Test
    void makeSubComment() {
        Random random = new Random();
        List<CulturalRelicComment> result = new ArrayList<>();
        for (int i = 0; i <= 500; i++) {
            int userId = random.nextInt(10);
            long parentCommentId = random.nextInt(200);
            CulturalRelicComment parentComment = culturalRelicCommentService.getById(parentCommentId);
            while (parentComment == null) {
                parentCommentId = random.nextInt(200);
                parentComment = culturalRelicCommentService.getById(parentCommentId);
            }
            CulturalRelicComment comment = CulturalRelicComment.builder()
                    .content("我是一条子评论" + i)
                    .likeNum(random.nextInt(100))
                    .publishUserId(userId)
                    .publishUserName("userName" + userId)
                    .status(1)
                    .parentCommentId(parentCommentId)
                    .culturalRelicId(parentComment.getCulturalRelicId())
                    .build();
            result.add(comment);
        }
        culturalRelicCommentService.saveBatch(result);
    }

}
