package com.buct.computer.controller;

import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.service.impl.CulturalRelicInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/16/21:44
 */
@SpringBootTest
public class CulturalRelicInfoControllerTest {

    @Autowired
    private CulturalRelicInfoServiceImpl culturalRelicInfoService;


    @Test
    void makeData() {
        List<CulturalRelicInfo> culturalRelicInfoList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            CulturalRelicInfo relicInfo = CulturalRelicInfo.builder()
                    .name("name-" + i)
                    .author("author-" + i)
                    .unearthedTime(new Date())
                    .detail("xxxxxxxxx")
                    .build();
            culturalRelicInfoList.add(relicInfo);
        }
        culturalRelicInfoService.saveBatch(culturalRelicInfoList);
    }

}
