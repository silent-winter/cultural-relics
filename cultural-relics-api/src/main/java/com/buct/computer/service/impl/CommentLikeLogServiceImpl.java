package com.buct.computer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buct.computer.model.CommentLikeLog;
import com.buct.computer.mapper.CommentLikeLogMapper;
import com.buct.computer.service.ICommentLikeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户评论点赞记录表 服务实现类
 * </p>
 *
 * @author xinzi
 * @since 2022-04-24
 */
@Service
public class CommentLikeLogServiceImpl extends ServiceImpl<CommentLikeLogMapper, CommentLikeLog> implements ICommentLikeLogService {

    @Override
    public List<CommentLikeLog> findByLikeUserId(Integer likeUserId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<CommentLikeLog>().eq(CommentLikeLog::getLikeUserId, likeUserId));
    }

    @Override
    public CommentLikeLog findByCommentIdAndLikeUserId(Long commentId, Integer likeUserId) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<CommentLikeLog>().eq(CommentLikeLog::getLikeUserId, likeUserId)
                        .eq(CommentLikeLog::getCommentId, commentId)
        );
    }

}
