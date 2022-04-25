package com.buct.computer.mapper;

import com.buct.computer.model.CommentLikeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户评论点赞记录表 Mapper 接口
 * </p>
 *
 * @author xinzi
 * @since 2022-04-24
 */
@Mapper
public interface CommentLikeLogMapper extends BaseMapper<CommentLikeLog> {

}
