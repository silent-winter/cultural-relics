package com.buct.computer.common.assembler;

import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.request.CulturalRelicInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/17/12:57
 */
@Mapper
public interface CulturalRelicAssembler {

    CulturalRelicAssembler MAPPER = Mappers.getMapper(CulturalRelicAssembler.class);


    @Mapping(target = "status", expression = "java(getStatusFromLocation(culturalRelicInfoDTO.getLocation()))")
    @Mapping(target = "imageUrl", source = "culturalRelicInfoDTO.imgName", qualifiedByName = "imageUrlConvert")
    @Mapping(target = "museum", source = "culturalRelicInfoDTO.location", qualifiedByName = "getMuseumFromLocation")
    @Mapping(target = "customClass", source = "culturalRelicInfoDTO.classification", qualifiedByName = "getCustomClassFromClassification")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(source = "culturalRelicInfoDTO.time", target = "discoverTime")
    @Mapping(target = "detail", source = "culturalRelicInfoDTO.details", qualifiedByName = "replaceSpecialCharacter")
    @Mapping(target = "artist", source = "culturalRelicInfoDTO.artist", qualifiedByName = "replaceSpecialCharacter")
    CulturalRelicInfo culturalRelicInfoDTOToCulturalRelicInfo(CulturalRelicInfoDTO culturalRelicInfoDTO, Integer likeNum);

    @Named("imageUrlConvert")
    default String imageUrlConvert(String imgName) {
        imgName = imgName.replaceAll("%", "%25");
        return "https://cultural-relics.oss-cn-beijing.aliyuncs.com/pic15/" + imgName;
    }

    @Named("getMuseumFromLocation")
    default String getMuseumFromLocation(String location) {
        if (StringUtils.isBlank(location)) {
            return StringUtils.EMPTY;
        }
        String[] split = StringUtils.split(location, ",");
        return split[split.length - 1].trim();
    }

    default Integer getStatusFromLocation(String location) {
        location = location.replaceAll("\\n|\\r", StringUtils.EMPTY).trim();
        if (StringUtils.startsWithAny(location, "on view", "On view")) {
            return 1;
        } else if (StringUtils.startsWithAny(location, "Not on view", "not on view")) {
            return 0;
        }
        // 没标识view属性，默认处于在展状态
        return 1;
    }

    @Named("replaceSpecialCharacter")
    default String replaceSpecialCharacter(String str) {
        return str.replaceAll("\\n|\\r", StringUtils.EMPTY).trim();
    }

    @Named("getCustomClassFromClassification")
    default String getCustomClassFromClassification(String classification) {
        if (StringUtils.equalsAnyIgnoreCase(classification, "Numismatics")) {
            return "Numismatics";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Containers - Ceramics", "Ceramics, containers",
                "Containers - Metals", "Containers, metalwork", "Containers - Wood", "Containers - Stone", "Ceramics",
                "Containers - Lacquer", "Containers - Glass", "Ceramics, models & maquettes", "Containers - Baskets",
                "Containers - Plastic", "Ceramics, sculpture", "Ceramics, tools & equipment", "Basketry, containers")) {
            return "Container";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Paintings",
                "Works on Paper - Other",
                "Works on Paper - Prints",
                "Works on Paper - Drawings and Watercolors",
                "Works on Paper - Photographs",
                "Works on Paper - Prints - Posters",
                "Works on Paper - Multiples")) {
            return "Paintings";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Sculpture",
                "Metalwork, sculpture",
                "Sculpture, stone & mineral",
                "Sculpture, wood",
                "Jades, sculpture")) {
            return "Sculpture";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Textiles",
                "Apparel/Costume",
                "Costume & clothing, jades")) {
            return "Costume";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Calligraphy")) {
            return "Calligraphy";
        } else if (StringUtils.equalsAnyIgnoreCase(classification, "Jewelry",
                "Furniture",
                "Architectural Elements",
                "Jades, jewelry & personal accessories")) {
            return "ArtWare";
        } else {
            return "Others";
        }
    }

}
