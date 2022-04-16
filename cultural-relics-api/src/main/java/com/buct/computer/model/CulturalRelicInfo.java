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
     * 出土时间(年月日)
     */
    @TableField("unearthed_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date unearthedTime;

    /**
     * 文物图片地址
     */
    @TableField("image")
    private String image;

    /**
     * 文物材质
     */
    @TableField("material")
    private String material;

    /**
     * 文物作者
     */
    @TableField("author")
    private String author;

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
     * 文物类别
     */
    @TableField("type")
    private String type;

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
