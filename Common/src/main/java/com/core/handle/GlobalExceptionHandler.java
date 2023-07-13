package com.core.handle;

//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
//import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
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

    /**
     * 处理参数校验异常
     * @param ex 异常对象
     * @return AjaxResult
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult BindException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMsgList = new LinkedList<>();
        allErrors.forEach(error -> errorMsgList.add("[ " + error.getDefaultMessage() + " ]"));
        Set<String> errorMsgSet = new LinkedHashSet<>(errorMsgList);
        return Build.ajax(false,String.join(",",errorMsgSet));
    }

    /**
     * 处理参数校验异常
     * @param ex 异常对象
     * @return AjaxResult
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult RuntimeException(RuntimeException ex){
        return Build.ajax(false,ex.getMessage());
    }

    /**
     * 处理Sentinel异常
     * @param ex 异常对象
     * @return AjaxResult
     */
//    @ExceptionHandler(BlockException.class)
//    public AjaxResult BlockException(BlockException ex){
//        String errorMsg = "系统异常";
//        int code = 429;
//        if(ex instanceof AuthorityException){
//            code = 401;
//            errorMsg = "权限不足,拒绝访问";
//        }else if (ex instanceof DegradeException){
//            errorMsg = "服务暂时不可用,请稍后重试!";
//        }else if (ex instanceof FlowException){
//            errorMsg = "短时间内请求次数过多,请稍后重试!";
//        }else if (ex instanceof ParamFlowException){
//            errorMsg = "热点参数限流,请稍后重试!";
//        }else if (ex instanceof SystemBlockException){
//            errorMsg = "系统异常";
//        }
//        return Build.buildAjax(code,errorMsg);
//    }
}
