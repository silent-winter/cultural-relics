package com.buct.computer.controller;


import com.buct.computer.common.assembler.CulturalRelicCommentAssembler;
import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.request.CulturalRelicCommentDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.CulturalRelicCommentVO;
import com.buct.computer.service.ICulturalRelicCommentService;
import com.buct.computer.service.ICulturalRelicInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文物评论表 前端控制器
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/comment")
public class CulturalRelicCommentController {

    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;
    @Autowired
    private ICulturalRelicCommentService culturalRelicCommentService;


    @GetMapping("/page")
    @ApiOperation("根据文物id查询所有评论得分页数据")
    public ApiResult<List<CulturalRelicCommentVO>> getPageComments(@RequestParam("culturalRelicId") Long culturalRelicId,
                                     @RequestParam(value = "page", required = false) Integer page,
                                     @RequestParam(value = "size", required = false) Integer size) {
        CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(culturalRelicId);
        if (culturalRelicInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        List<CulturalRelicCommentVO> commentList = culturalRelicCommentService.getPageCommentList(culturalRelicId, page, size);
        return ApiResult.success(commentList);
    }


    @PostMapping("/like/{id}")
    @ApiOperation("评论点赞")
    public ApiResult<CulturalRelicComment> doLike(@PathVariable("id") Long commentId) {
        CulturalRelicComment comment = culturalRelicCommentService.getById(commentId);
        if (comment == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        comment.setLikeNum(comment.getLikeNum() + 1);
        culturalRelicCommentService.updateById(comment);
        return ApiResult.success(comment);
    }


    @PostMapping("/publish")
    @ApiOperation("发表评论")
    public ApiResult<CulturalRelicComment> publishComment(@RequestBody CulturalRelicCommentDTO culturalRelicCommentDTO) {
        CulturalRelicComment comment = CulturalRelicCommentAssembler.MAPPER
                .CulturalRelicCommentDTOToCulturalRelicComment(culturalRelicCommentDTO);
        comment.setLikeNum(0);
        comment.setStatus(1);
        culturalRelicCommentService.save(comment);
        return ApiResult.success(comment);
    }


    @PostMapping("/delete/{id}")
    @ApiOperation("删除评论")
    public ApiResult<List<Long>> deleteComment(@PathVariable("id") Long commentId) {
        List<Long> needRemoveIds = new ArrayList<>();
        CulturalRelicComment comment = culturalRelicCommentService.getById(commentId);
        if (comment == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        // 如果是父评论，删除所有子评论
        if (comment.getParentCommentId() == null) {
            List<CulturalRelicComment> children = culturalRelicCommentService.getAllSubCommentsByParentId(commentId);
            needRemoveIds = children.stream().map(CulturalRelicComment::getId).collect(Collectors.toList());
        }
        needRemoveIds.add(commentId);
        culturalRelicCommentService.removeByIds(needRemoveIds);
        return ApiResult.success(needRemoveIds);
    }

}
