package com.buct.computer.request;

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
public class CulturalRelicCommentDTO {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Long parentCommentId;

    /**
     * 该条评论归属的文物id
     */
    private Long culturalRelicId;

    /**
     * 发布者id
     */
    private Integer publishUserId;

    /**
     * 发布者用户名
     */
    private String publishUserName;

}
