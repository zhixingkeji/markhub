package com.zhixingkeji.api.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value=IllegalArgumentException.class)
    public ResultTemplate handler(IllegalArgumentException e){
        log.error("Assert异常",e.getMessage());
        return ResultTemplate.fail(e.getMessage());
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value=RuntimeException.class)
    public ResultTemplate handler(RuntimeException e){
        log.error("运行时异常",e.getMessage());
        return ResultTemplate.fail(e.getMessage());
    }

}
