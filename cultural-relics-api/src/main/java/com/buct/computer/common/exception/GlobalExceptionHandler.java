package com.buct.computer.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.buct.computer.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 全局异常处理器
 * @Auther: xinzi
 * @Date: 2022/04/16/21:17
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidParamException.class)
    public ApiResult<String> handleInvalidParamException(InvalidParamException e){
        log.warn("handleInvalidParamException detected!", e);
        return ApiResult.fail(ApiResult.ERROR_PARAM, ApiResult.ERROR_PARAM_MSG);
    }

    @ExceptionHandler(NotLoginException.class)
    public ApiResult<String> handleNotLoginException(NotLoginException e){
        log.warn("handleNotLoginException detected!", e);
        return ApiResult.fail("401", "please login first");
    }

    @ExceptionHandler(NotRoleException.class)
    public ApiResult<String> handleNotRoleException(NotRoleException e){
        log.warn("handleNotRoleException detected!", e);
        return ApiResult.fail("401", "only admin can access");
    }

    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(Exception e){
        log.warn("handleException detected!", e);
        return ApiResult.fail(ApiResult.ERROR_SYSTEM, ApiResult.ERROR_SYSTEM_MSG);
    }

}
