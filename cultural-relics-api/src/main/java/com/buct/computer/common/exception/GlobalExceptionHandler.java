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

    @ExceptionHandler(Exception.class)
    public ApiResult<String> handleException(Exception e){
        log.warn("handleException detected");
        return ApiResult.fail("Unknown");
    }

}
