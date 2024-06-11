package com.core.handle;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.core.doMain.Logstash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @Value("${spring.application.name:未知}")
    private String applicationName;


    /**
     * 处理未定义异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception ex, HandlerMethod handlerMethod){
        ex.printStackTrace();
        Logstash.log(ex, handlerMethod,applicationName);
        return Build.ajax(500,"服务异常");
    }


    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException ex, HandlerMethod handlerMethod){
        ex.printStackTrace();
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String handlerErrorMsg = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        Logstash.log(ex,handlerMethod,applicationName,handlerErrorMsg);
        return Build.ajax(false,handlerErrorMsg);
    }


    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException ex, HandlerMethod handlerMethod){
        ex.printStackTrace();
        Logstash.log(ex,handlerMethod,applicationName);
        return Build.ajax(false,ex.getMessage());
    }



    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public AjaxResult handleNullPointerException(NullPointerException ex, HandlerMethod handlerMethod){
        ex.printStackTrace();
        Logstash.log(ex,handlerMethod,applicationName,"空指针异常");
        return Build.ajax(false,ex.getMessage());
    }
}
