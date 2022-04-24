package com.buct.computer.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * <p>
 * 用户评论点赞记录表
 * </p>
 *
 * @author xinzi
 * @since 2022-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment_like_log")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentLikeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户被点赞的评论id
     */
    @TableField("comment_id")
    private Long commentId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 点赞者id
     */
    @TableField("like_user_id")
    private Integer likeUserId;

    /**
     * 点赞者用户名
     */
    @TableField("like_user_name")
    private String likeUserName;

    /**
     * 通知标志，用户查看通知后标记为0
     */
    @TableField("notice_flag")
    private Boolean noticeFlag;

    /**
     * 记录创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 记录修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
