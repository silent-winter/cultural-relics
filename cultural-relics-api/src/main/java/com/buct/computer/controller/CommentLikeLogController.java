package com.buct.computer.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buct.computer.model.CommentLikeLog;
import com.buct.computer.response.ApiResult;
import com.buct.computer.service.ICommentLikeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户评论点赞记录表 前端控制器
 * </p>
 *
 * @author xinzi
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/commentLikeLog")
@Api(tags = "评论点赞记录相关接口")
public class CommentLikeLogController {

    @Autowired
    private ICommentLikeLogService commentLikeLogService;


    @GetMapping("/beLikedComments")
    @ApiOperation("查询当前登录用户哪些评论被点过赞")
    public ApiResult<List<CommentLikeLog>> getUserLike() {
        Integer userId = StpUtil.getLoginId(0);
        List<CommentLikeLog> commentLikeLogs = commentLikeLogService.getBaseMapper()
                .selectList(new LambdaQueryWrapper<CommentLikeLog>().eq(CommentLikeLog::getUserId, userId));
        return ApiResult.success(commentLikeLogs);
    }


    @GetMapping("/likeComments")
    @ApiOperation("查询指定用户点赞的评论")
    public ApiResult<List<CommentLikeLog>> getUserLike(@RequestParam(value = "userId", required = false) Integer userId) {
        if (Objects.isNull(userId)) {
            userId = StpUtil.getLoginId(0);
        }
        List<CommentLikeLog> commentLikeLogs = commentLikeLogService.getBaseMapper()
                .selectList(new LambdaQueryWrapper<CommentLikeLog>().eq(CommentLikeLog::getLikeUserId, userId));
        return ApiResult.success(commentLikeLogs);
    }

}
