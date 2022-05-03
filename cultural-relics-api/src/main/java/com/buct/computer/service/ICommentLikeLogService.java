package com.buct.computer.service;

import com.buct.computer.model.CommentLikeLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户评论点赞记录表 服务类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-24
 */
public interface ICommentLikeLogService extends IService<CommentLikeLog> {

    List<CommentLikeLog> findByLikeUserId(Integer likeUserId);

    CommentLikeLog findByCommentIdAndLikeUserId(Long commentId, Integer likeUserId);

}
