package com.buct.computer.common.assembler;

import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.request.CulturalRelicCommentDTO;
import com.buct.computer.response.vo.CulturalRelicCommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/23/13:21
 */
@Mapper
public interface CulturalRelicCommentAssembler {

    CulturalRelicCommentAssembler MAPPER = Mappers.getMapper(CulturalRelicCommentAssembler.class);

    @Mapping(target = "childrenCommentList", ignore = true)
    @Mapping(target = "commentId", source = "id")
    @Mapping(source = "updateTime", target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CulturalRelicCommentVO CulturalRelicCommentToCulturalRelicCommentVO(CulturalRelicComment culturalRelicComment);



    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "likeNum", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    CulturalRelicComment CulturalRelicCommentDTOToCulturalRelicComment(CulturalRelicCommentDTO culturalRelicCommentDTO);

}
