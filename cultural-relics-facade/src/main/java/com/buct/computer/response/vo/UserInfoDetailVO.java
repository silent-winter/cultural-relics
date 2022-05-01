package com.buct.computer.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @Auther: xinzi
 * @Date: 2022/04/24/18:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserInfoDetailVO {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像图片地址")
    private String profilePicture;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("最近三天浏览的文物id列表")
    private Set<Long> browseIds;

    @ApiModelProperty("用户收藏的文物id列表")
    private Set<Long> collectIds;

    @ApiModelProperty("用户点赞的文物id列表")
    private Set<Long> likeIds;

}
