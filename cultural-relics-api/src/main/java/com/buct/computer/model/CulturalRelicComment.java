package com.buct.computer.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * <p>
 * 文物评论表
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cultural_relic_comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturalRelicComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 点赞数
     */
    @TableField("like_num")
    private Integer likeNum;

    /**
     * 父评论id，如果是一级评论(指向文物)则为null
     */
    @TableField("parent_comment_id")
    private Long parentCommentId;

    /**
     * 该条评论归属的文物id(父子评论相同)
     */
    @TableField("cultural_relic_id")
    private Long culturalRelicId;

    /**
     * 发布者id
     */
    @TableField("publish_user_id")
    private Integer publishUserId;

    /**
     * 发布者用户名
     */
    @TableField("publish_user_name")
    private String publishUserName;

    /**
     * 评论状态(1:正常评论，0:评论违规，不可见)
     */
    @TableField("status")
    private Integer status;

    /**
     * 评论修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 评论创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
