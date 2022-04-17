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
 * 文物具体信息表
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cultural_relic_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturalRelicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文物id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文物名称
     */
    @TableField("name")
    private String name;

    /**
     * 文物发现时间信息
     */
    @TableField("discover_time")
    private String discoverTime;

    /**
     * 文物图片地址
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 文物材质
     */
    @TableField("medium")
    private String medium;

    /**
     * 文物作者
     */
    @TableField("artist")
    private String artist;

    /**
     * 文物在展情况(1:在展，0:下架)
     */
    @TableField("status")
    private Integer status;

    /**
     * 文物详情介绍
     */
    @TableField("detail")
    private String detail;

    /**
     * 文物尺寸
     */
    @TableField("dimension")
    private String dimension;

    /**
     * 文物类别
     */
    @TableField("classification")
    private String classification;

    /**
     * 详情页地址
     */
    @TableField("detail_url")
    private String detailUrl;

    /**
     * 文物所在地址信息
     */
    @TableField("location")
    private String location;

    /**
     * 记录创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 记录修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
