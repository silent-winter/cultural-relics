package com.buct.computer.service;

import com.buct.computer.model.CulturalRelicComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buct.computer.response.vo.CulturalRelicCommentVO;

import java.util.List;

/**
 * <p>
 * 文物评论表 服务类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
public interface ICulturalRelicCommentService extends IService<CulturalRelicComment> {

    List<CulturalRelicComment> getAllCommentsByCulturalRelicIdAndStatus(Long culturalRelicId, Integer status);

    List<CulturalRelicCommentVO> getPageCommentList(Long culturalRelicId, Integer page, Integer size);

    List<CulturalRelicComment> getAllSubCommentsByParentId(Long parentCommentId);

}
