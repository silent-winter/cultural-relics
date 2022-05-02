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


    @Test
    void fillCustomClass() {
        List<CulturalRelicInfo> culturalRelicInfoList = culturalRelicInfoService.getBaseMapper().selectList(null);
        culturalRelicInfoList = culturalRelicInfoList.stream().map(e -> {
            String classification = e.getClassification();
            if (StringUtils.equalsAnyIgnoreCase(classification, "Numismatics")) {
                e.setCustomClass("Numismatics");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Containers - Ceramics", "Ceramics, containers",
                    "Containers - Metals", "Containers, metalwork", "Containers - Wood", "Containers - Stone", "Ceramics",
                    "Containers - Lacquer", "Containers - Glass", "Ceramics, models & maquettes", "Containers - Baskets",
                    "Containers - Plastic", "Ceramics, sculpture", "Ceramics, tools & equipment", "Basketry, containers")) {
                e.setCustomClass("Container");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Paintings",
                    "Works on Paper - Other",
                    "Works on Paper - Prints",
                    "Works on Paper - Drawings and Watercolors",
                    "Works on Paper - Photographs",
                    "Works on Paper - Prints - Posters",
                    "Works on Paper - Multiples")) {
                e.setCustomClass("Paintings");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Sculpture",
                    "Metalwork, sculpture",
                    "Sculpture, stone & mineral",
                    "Sculpture, wood",
                    "Jades, sculpture")) {
                e.setCustomClass("Sculpture");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Textiles",
                    "Apparel/Costume",
                    "Costume & clothing, jades")) {
                e.setCustomClass("Costume");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Calligraphy")) {
                e.setCustomClass("Calligraphy");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Jewelry",
                    "Furniture",
                    "Architectural Elements",
                    "Jades, jewelry & personal accessories")) {
                e.setCustomClass("ArtWare");
            } else if (StringUtils.equalsAnyIgnoreCase(classification, "Tools and Equipment",
                    "Containers - Other",
                    "Flatware",
                    "Arms and Armor",
                    "Jades",
                    "Arms & armor, jades",
                    "Musical Instruments",
                    "Metalwork, tools & equipment",
                    "Jades, stone & mineral",
                    "Miniatures",
                    "Containers, jades",
                    "Ritual Objects",
                    "Lighting Devices",
                    "Stained Glass",
                    "Timepieces",
                    "Toys and Games",
                    "Metalwork",
                    "Metalwork, musical instruments",
                    "Arms & armor, metalwork",
                    "Jewelry & personal accessories, metalwork",
                    "Ceramics, jewelry & personal accessories")) {
                e.setCustomClass("Others");
            }
            return e;
        }).collect(Collectors.toList());
        culturalRelicInfoService.updateBatchById(culturalRelicInfoList);
    }

}
