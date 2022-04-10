package com.buct.computer.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 应用模块名称<p></p>
 * 代码描述<p></p>
 * Copyright: Copyright (C) 2019 Bytedance，Inc. All rights reserved. <p>
 * Company: 北京字节跳动科技有限公司 Enterprise Application <p>
 *
 * @author xinzi.agm
 * @since 2022/4/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ApiResult<T> {

    public static final String SUCCESS = "A00000";              //成功
    public static final String ERROR_SYSTEM = "Q00500";         //未知异常，系统错误
    public static final String ERROR_PARAM = "Q00301";          //输入参数错误
    public static final String NO_AUTH_MESSAGE = "Q00403";      //没有权限
    public static final String ENTITY_ABSENT = "Q00404";        //数据库实例不存在


    public static final String SUCCESS_MESSAGE = "success";
    public static final String FAIL_MESSAGE = "fail";


    private Boolean success;

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, ApiResult.SUCCESS, SUCCESS_MESSAGE, data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(false, ApiResult.ERROR_SYSTEM, FAIL_MESSAGE, null);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(false, ApiResult.ERROR_SYSTEM, msg, null);
    }


    @JsonIgnore
    public boolean isSuccessful() {
        return Boolean.TRUE.equals(this.success) || SUCCESS.equals(this.code);
    }

}
