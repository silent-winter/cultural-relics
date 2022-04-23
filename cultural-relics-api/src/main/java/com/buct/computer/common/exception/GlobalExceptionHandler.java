package com.buct.computer.common.exception;

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

    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(Exception e){
        log.warn("handleException detected!", e);
        return ApiResult.fail(ApiResult.ERROR_SYSTEM, ApiResult.ERROR_SYSTEM_MSG);
    }

}
