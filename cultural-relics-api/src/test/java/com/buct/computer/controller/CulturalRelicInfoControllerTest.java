package com.buct.computer.controller;

import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.service.ICulturalRelicInfoService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private ICulturalRelicInfoService culturalRelicInfoService;


    @Test
    void makeData() {
        List<CulturalRelicInfo> culturalRelicInfoList = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            CulturalRelicInfo relicInfo = CulturalRelicInfo.builder()
                    .name("name-" + i)
                    .artist("author-" + i)
                    .discoverTime("time")
                    .detail("xxxxxxxxx")
                    .build();
            culturalRelicInfoList.add(relicInfo);
        }
        culturalRelicInfoService.saveBatch(culturalRelicInfoList);
    }

    @Test
    void handleStatus() {
        List<CulturalRelicInfo> culturalRelicInfoList = culturalRelicInfoService.getBaseMapper().selectList(null);
        List<CulturalRelicInfo> saved = new ArrayList<>();
        culturalRelicInfoList.stream().forEach(e -> {
            String location = e.getLocation();
            if (StringUtils.startsWithAny(location, "Not on view", "not on view")) {
                e.setStatus(0);
                saved.add(e);
            }
        });
        culturalRelicInfoService.updateBatchById(saved);
    }

    @Test
    void clear() {
        List<CulturalRelicInfo> culturalRelicInfoList = culturalRelicInfoService.getBaseMapper().selectList(null);
        culturalRelicInfoList = culturalRelicInfoList.stream().map(e -> {
            String artist = e.getArtist();
            artist = artist.replaceAll(" ", " ");
            e.setArtist(artist);

            String a = e.getClassification();
            a = a.replaceAll(" ", " ");
            e.setClassification(a);

            String b = e.getDetail();
            b = b.replaceAll(" ", " ");
            e.setDetail(b);

            String c = e.getDimension();
            c = c.replaceAll(" ", " ");
            e.setDimension(c);

            String d = e.getDiscoverTime();
            d = d.replaceAll(" ", " ");
            e.setDiscoverTime(d);

            String f = e.getLocation();
            f = f.replaceAll(" ", " ");
            e.setLocation(f);

            String g = e.getName();
            g = g.replaceAll(" ", " ");
            e.setName(g);
            return e;
        }).collect(Collectors.toList());
        culturalRelicInfoService.updateBatchById(culturalRelicInfoList);
    }

}
