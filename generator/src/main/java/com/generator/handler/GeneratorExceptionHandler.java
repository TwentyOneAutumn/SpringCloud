package com.generator.handler;

import com.generator.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GeneratorExceptionHandler {

    /**
     * 处理参数校验异常
     * @param ex 异常对象
     * @return AjaxResult
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult BindException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
        log.error(errorMsg);
        return Build.ajax(false,errorMsg);
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult Exception(Exception ex){
        return Build.ajax(500,"服务异常");
    }
}
