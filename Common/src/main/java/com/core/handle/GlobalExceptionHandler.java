package com.core.handle;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public AjaxResult BindException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMsgList = new LinkedList<>();
        allErrors.forEach(error -> errorMsgList.add("[ " + error.getDefaultMessage() + " ]"));
        Set<String> errorMsgSet = new LinkedHashSet<>(errorMsgList);
        return Build.buildAjax(false,String.join(",",errorMsgSet));
    }
}
