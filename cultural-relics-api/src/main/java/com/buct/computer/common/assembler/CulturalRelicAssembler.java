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


    @Mapping(target = "status", source = "location", qualifiedByName = "getStatusFromLocation")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(source = "time", target = "discoverTime")
    @Mapping(source = "details", target = "detail")
    @Mapping(target = "imageUrl", source = "imgName", qualifiedByName = "imageUrlConvert")
    CulturalRelicInfo culturalRelicInfoDTOToCulturalRelicInfo(CulturalRelicInfoDTO culturalRelicInfoVO);

    @Named("imageUrlConvert")
    default String imageUrlConvert(String imgName) {
        return "https://cultural-relics.oss-cn-beijing.aliyuncs.com/pic/" + imgName;
    }

    @Named("getStatusFromLocation")
    default Integer getStatusFromLocation(String location) {
        location = location.replace("\n|\r", StringUtils.EMPTY).trim();
        if (StringUtils.startsWithAny(location, "on view", "On view")) {
            return 1;
        } else if (StringUtils.startsWithAny(location, "Not on view", "not on view")) {
            return 0;
        }
        // 没标识view属性，默认处于在展状态
        return 1;
    }

}
