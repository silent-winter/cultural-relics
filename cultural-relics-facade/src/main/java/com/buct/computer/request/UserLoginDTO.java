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
 * @Date: 2022/04/30/15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserLoginDTO {

    @ApiModelProperty(value = "用户名", required = true, example = "test")
    private String userName;

    @ApiModelProperty(value = "密码", required = true, example = "test")
    private String password;

}
