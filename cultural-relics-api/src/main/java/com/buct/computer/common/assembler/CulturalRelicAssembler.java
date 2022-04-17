package com.buct.computer.common.assembler;

import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.request.CulturalRelicInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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


    @Mapping(target = "status", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(source = "time", target = "discoverTime")
    @Mapping(source = "details", target = "detail")
    @Mapping(source = "photoUrl", target = "imageUrl")
    @Mapping(target = "id", ignore = true)
    CulturalRelicInfo culturalRelicInfoVOToCulturalRelicInfo(CulturalRelicInfoVO culturalRelicInfoVO);

}
