package com.core.config;

import com.core.doMain.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public AjaxResult Exception(Exception ex){
        return AjaxResult.error(ex.getMessage());
    }
}
