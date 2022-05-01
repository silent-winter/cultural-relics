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
 * @Date: 2022/04/30/15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserRegisterDTO {

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户头像")
    private String profilePicture;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户类型")
    private String type;

}
