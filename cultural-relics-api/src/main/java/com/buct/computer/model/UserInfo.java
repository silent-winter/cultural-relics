package com.buct.computer.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xinzi
 * @since 2022-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码(md5加密值)
     */
    @TableField("password")
    private String password;

    /**
     * 头像图片地址
     */
    @TableField("profile_picture")
    private String profilePicture;

    /**
     * 个性签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户类型(管理员、普通用户)
     */
    @TableField("type")
    @ApiModelProperty(hidden = true)
    private String type;

    /**
     * 用户收藏的文物id集合
     */
    @TableField("collection")
    @ApiModelProperty(hidden = true)
    private String collection;

    /**
     * 用户点赞的文物id集合
     */
    @TableField("like_cultural_relics")
    @ApiModelProperty(hidden = true)
    private String likeCulturalRelics;

    /**
     * 是否可用
     */
    @TableField("status")
    @ApiModelProperty(hidden = true)
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

}
