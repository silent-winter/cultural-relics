package com.buct.computer.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * <p>
 * 用户浏览记录表
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_browse_log")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBrowseLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户浏览的文物id
     */
    @TableField("cultural_relic_id")
    private Long culturalRelicId;

    /**
     * 用户浏览时间点
     */
    @TableField("browse_time")
    private Date browseTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
