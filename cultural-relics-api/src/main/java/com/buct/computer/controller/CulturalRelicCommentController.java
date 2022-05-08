package com.buct.computer.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.buct.computer.common.assembler.CulturalRelicCommentAssembler;
import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.model.CulturalRelicInfo;
import com.buct.computer.model.UserInfo;
import com.buct.computer.request.CulturalRelicCommentDTO;
import com.buct.computer.request.LikeRequestDTO;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.CulturalRelicCommentVO;
import com.buct.computer.service.ICulturalRelicCommentService;
import com.buct.computer.service.ICulturalRelicInfoService;
import com.buct.computer.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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
@Api(tags = "用户评论相关接口")
public class CulturalRelicCommentController {

    private final ConcurrentHashMap<Long, Object> lockMap = new ConcurrentHashMap<>();

    @Autowired
    private ICulturalRelicInfoService culturalRelicInfoService;
    @Autowired
    private ICulturalRelicCommentService culturalRelicCommentService;
    @Autowired
    private IUserInfoService userInfoService;


    @GetMapping("/page")
    @ApiOperation("根据文物id查询所有评论得分页数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "culturalRelicId", value = "文物id", defaultValue = "1", required = true), @ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1"), @ApiImplicitParam(name = "size", value = "每页记录数", defaultValue = "10")})
    public ApiResult<List<CulturalRelicCommentVO>> getPageComments(@RequestParam("culturalRelicId") Long culturalRelicId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        CulturalRelicInfo culturalRelicInfo = culturalRelicInfoService.getById(culturalRelicId);
        if (culturalRelicInfo == null) {
            return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
        }
        List<CulturalRelicCommentVO> commentList = culturalRelicCommentService.getPageCommentList(culturalRelicId, page, size);
        return ApiResult.success(commentList);
    }


    @PostMapping("/handleLike")
    @ApiOperation("评估点赞或者是取消点赞")
    public ApiResult<CulturalRelicComment> handleLike(@RequestBody LikeRequestDTO likeRequestDTO) {
        Long commentId = likeRequestDTO.getTargetId();
        lockMap.putIfAbsent(commentId, new Object());
        synchronized (lockMap.get(commentId)) {
            CulturalRelicComment comment = culturalRelicCommentService.getById(commentId);
            if (comment == null) {
                return ApiResult.fail(ApiResult.ENTITY_ABSENT, ApiResult.ENTITY_ABSENT_MSG);
            }
            return ApiResult.success(culturalRelicCommentService.likeOrUnlike(comment, likeRequestDTO.getIsAdd()));
        }
    }


    @PostMapping("/publish")
    @ApiOperation("发表评论")
    public ApiResult<CulturalRelicComment> publishComment(@RequestBody CulturalRelicCommentDTO culturalRelicCommentDTO) {
        UserInfo loginUser = userInfoService.getLoginUser();
        CulturalRelicComment comment = CulturalRelicCommentAssembler.MAPPER
                .CulturalRelicCommentDTOToCulturalRelicComment(culturalRelicCommentDTO, 0, 1, loginUser.getId(), loginUser.getUserName());
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
