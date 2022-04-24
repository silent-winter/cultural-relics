package com.buct.computer.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/23/12:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class CulturalRelicCommentVO {

    /**
     * 评论id
     */
    @ApiModelProperty("评论id")
    private Long commentId;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 点赞数
     */
    @ApiModelProperty("点赞数")
    private Integer likeNum;

    /**
     * 父评论id
     */
    @ApiModelProperty("父评论id，一级评论该值为null")
    private Long parentCommentId;

    /**
     * 发布者id
     */
    @ApiModelProperty("发布者id")
    private Integer publishUserId;

    /**
     * 发布者用户名
     */
    @ApiModelProperty("发布者用户名")
    private String publishUserName;

    /**
     * 评论最新更新时间
     */
    @ApiModelProperty("评论最新更新时间")
    private Date updateTime;

    /**
     * 子评论
     */
    @ApiModelProperty("子评论数组")
    private List<CulturalRelicCommentVO> childrenCommentList;

}
