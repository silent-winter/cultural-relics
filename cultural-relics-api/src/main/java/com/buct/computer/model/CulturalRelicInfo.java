package com.buct.computer.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
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

    @JsonIgnore
    private static final Set<String> VALID_SORT_FIELDS = ImmutableSet.of(
            "id", "name", "artist", "custom_class", "museum", "like_num"
    );

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
     * 自定义分类，查询时所用
     */
    @TableField("custom_class")
    private String customClass;

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
     * 文物所属博物馆
     */
    @TableField("museum")
    private String museum;

    /**
     * 文物所在地址信息
     */
    @TableField("like_num")
    private Integer likeNum;

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


    @JsonIgnore
    public static boolean isValidSortField(String field) {
        return VALID_SORT_FIELDS.contains(field);
    }

}
