package com.core.handle;

import com.core.doMain.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public AjaxResult Exception(Exception ex){
        return AjaxResult.error(ex.getMessage());
    }
}
