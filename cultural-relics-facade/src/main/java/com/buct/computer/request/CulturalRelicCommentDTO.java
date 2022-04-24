package com.buct.computer.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/23/19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class CulturalRelicCommentDTO {

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 父评论id
     */
    @ApiModelProperty("父评论id，如果是一级评论，直接传null")
    private Long parentCommentId;

    /**
     * 该条评论归属的文物id
     */
    @ApiModelProperty("该条评论归属的文物id")
    private Long culturalRelicId;

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

}
