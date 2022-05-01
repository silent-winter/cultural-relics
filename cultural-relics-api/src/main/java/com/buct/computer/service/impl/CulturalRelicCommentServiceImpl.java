package com.buct.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.computer.common.assembler.CulturalRelicCommentAssembler;
import com.buct.computer.common.exception.InvalidParamException;
import com.buct.computer.model.CommentLikeLog;
import com.buct.computer.model.CulturalRelicComment;
import com.buct.computer.mapper.CulturalRelicCommentMapper;
import com.buct.computer.model.UserInfo;
import com.buct.computer.response.ApiResult;
import com.buct.computer.response.vo.CulturalRelicCommentVO;
import com.buct.computer.service.ICommentLikeLogService;
import com.buct.computer.service.ICulturalRelicCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct.computer.service.IUserInfoService;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 文物评论表 服务实现类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Service
public class CulturalRelicCommentServiceImpl extends ServiceImpl<CulturalRelicCommentMapper, CulturalRelicComment>
        implements ICulturalRelicCommentService {

    @Autowired
    private ICommentLikeLogService commentLikeLogService;
    @Autowired
    private IUserInfoService userInfoService;


    @Override
    public List<CulturalRelicCommentVO> getPageCommentList(Long culturalRelicId, Integer page, Integer size) {
        // 先根据文物id查询所有的评论
        List<CulturalRelicComment> comments = this.getAllCommentsByCulturalRelicIdAndStatus(culturalRelicId, 1);
        Set<Long> effectCommentIds;
        if (Objects.nonNull(page) && Objects.nonNull(size)) {
            // 需要分页查找，筛选出相应的父评论id
            checkPageParam(page, size);
            List<CulturalRelicComment> parentComments =
                    this.getPageParentCommentsByCulturalRelicIdAndStatus(new Page<>(page, size), culturalRelicId, 1);
            effectCommentIds = parentComments.stream().map(CulturalRelicComment::getId).collect(Collectors.toSet());
        } else {
            // 不需要分页，直接在所有评论里过滤即可
            effectCommentIds = comments.stream()
                    .filter(e -> e.getParentCommentId() == null)
                    .map(CulturalRelicComment::getId).collect(Collectors.toSet());
        }
        // 筛选所有父评论
        Map<Long, CulturalRelicCommentVO> parentCommentMap = comments.stream()
                .filter(comment -> effectCommentIds.contains(comment.getId()))
                .map(comment -> {
                    CulturalRelicCommentVO commentVO = CulturalRelicCommentAssembler.MAPPER
                            .CulturalRelicCommentToCulturalRelicCommentVO(comment);
                    commentVO.setChildrenCommentList(new ArrayList<>());
                    return commentVO;
                }).collect(Collectors.toMap(CulturalRelicCommentVO::getCommentId, Function.identity(), (a, b) -> b));
        // 构造子评论list
        comments.stream().filter(comment -> comment.getParentCommentId() != null && comment.getParentCommentId() > 0
                        && effectCommentIds.contains(comment.getParentCommentId()))
                .forEach(comment -> {
                    CulturalRelicCommentVO commentVO = CulturalRelicCommentAssembler.MAPPER
                            .CulturalRelicCommentToCulturalRelicCommentVO(comment);
                    commentVO.setChildrenCommentList(new ArrayList<>());
                    parentCommentMap.get(commentVO.getParentCommentId()).getChildrenCommentList().add(commentVO);
                });
        return parentCommentMap.values().stream()
                .sorted(Comparator.comparing(CulturalRelicCommentVO::getUpdateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<CulturalRelicComment> getAllSubCommentsByParentId(Long parentCommentId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<CulturalRelicComment>()
                .eq(CulturalRelicComment::getParentCommentId, parentCommentId));
    }

    @Override
    @Transactional
    public CulturalRelicComment likeOrUnlike(CulturalRelicComment comment, boolean isLike) {
        Integer likeNum = comment.getLikeNum();
        comment.setLikeNum(isLike ? likeNum + 1 : likeNum - 1);
        this.updateById(comment);
        // 保存点赞记录
        UserInfo user = userInfoService.getById(StpUtil.getLoginId(0));
        Asserts.notNull(user, "当前用户未登录");
        CommentLikeLog commentLikeLog = CommentLikeLog.builder()
                .commentId(comment.getId())
                .userId(comment.getPublishUserId())
                .likeUserId(user.getId())
                .likeUserName(user.getUserName())
                .noticeFlag(true)
                .build();
        commentLikeLogService.save(commentLikeLog);
        return comment;
    }

    @Override
    public List<CulturalRelicComment> getAllCommentsByCulturalRelicIdAndStatus(Long culturalRelicId, Integer status) {
        // 根据updateTime排序
        return this.baseMapper.selectList(new LambdaQueryWrapper<CulturalRelicComment>()
                .eq(CulturalRelicComment::getCulturalRelicId, culturalRelicId).eq(CulturalRelicComment::getStatus, status)
                .orderByDesc(CulturalRelicComment::getUpdateTime));
    }


    private void checkPageParam(Integer page, Integer size) {
        // 参数检查
        if (page <= 0 || size < 0) {
            throw new InvalidParamException(ApiResult.ERROR_PARAM_MSG);
        }
    }

    /**
     * 获取父评论list分页数据
     * @param page 分页参数
     * @param culturalRelicId 文物id
     * @param status 评论状态
     */
    private List<CulturalRelicComment> getPageParentCommentsByCulturalRelicIdAndStatus(Page<CulturalRelicComment> page,
                                                                                       Long culturalRelicId,
                                                                                       Integer status) {
        return this.baseMapper.selectPage(page, new LambdaQueryWrapper<CulturalRelicComment>()
                .isNull(CulturalRelicComment::getParentCommentId).eq(CulturalRelicComment::getStatus, status)
                .eq(CulturalRelicComment::getCulturalRelicId, culturalRelicId)).getRecords();
    }

}
