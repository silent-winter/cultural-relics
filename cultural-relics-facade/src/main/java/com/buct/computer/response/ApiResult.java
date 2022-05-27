package com.buct.computer.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
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
@ApiModel
public class ApiResult<T> {

    // 成功
    public static final String SUCCESS = "A00000";
    // 未知异常，系统错误
    public static final String ERROR_SYSTEM = "Q00500";
    // 输入参数错误
    public static final String ERROR_PARAM = "Q00301";
    // 没有权限
    public static final String NO_AUTH = "Q00403";
    // 数据库实例不存在
    public static final String ENTITY_ABSENT = "Q00404";
    // 敏感评论
    public static final String SENSITIVE_COMMENT = "Q00999";

    public static final String SUCCESS_MSG = "success";
    public static final String ERROR_SYSTEM_MSG = "未知异常，系统错误";
    public static final String ENTITY_ABSENT_MSG = "数据库实例不存在";
    public static final String ERROR_PARAM_MSG = "输入参数错误";
    public static final String NO_AUTH_MSG = "输入参数错误";
    public static final String SENSITIVE_COMMENT_MSG = "评论带有敏感字眼";


    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, ApiResult.SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(false, ApiResult.ERROR_SYSTEM, ERROR_SYSTEM_MSG, null);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(false, ApiResult.ERROR_SYSTEM, msg, null);
    }

    public static <T> ApiResult<T> fail(String code, String msg) {
        return new ApiResult<>(false, code, msg, null);
    }


    @JsonIgnore
    public boolean isSuccessful() {
        return Boolean.TRUE.equals(this.success) || SUCCESS.equals(this.code);
    }

}
