package com.server.retry.config;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.interceptor.MethodInvocationRetryCallback;

@Slf4j
@Configuration
public class RetryL implements RetryListener {


    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        return true;
    }

    /**
     * 该回调在整个重试操作结束后调用
     */
    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // 获取重试方法名称
        MethodInvocationRetryCallback<T, E> methodCallback = (MethodInvocationRetryCallback<T, E>) callback;
        String methodName = methodCallback.getInvocation().getMethod().getName();
        // 获取重试次数
        int retryCount = context.getRetryCount();
        // 判断重试是否成功
        if(BeanUtil.isNotEmpty(throwable)){
            log.error("{}()方法发生经过{}次重试最终放弃执行",methodName,retryCount);
        }else {
            log.error("{}()方法发生经过{}次重试最终成功执行",methodName,retryCount);
        }
    }


    /**
     * 该回调每次重试失败后调用
     */
    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // 获取重试方法名称
        MethodInvocationRetryCallback<T, E> methodCallback = (MethodInvocationRetryCallback<T, E>) callback;
        String methodName = methodCallback.getInvocation().getMethod().getName();
        // 获取重试次数
        int retryCount = context.getRetryCount();
        // 获取异常信息
        String errorMessage = throwable.getMessage();
        String errorClassName = throwable.getClass().getName();
        log.error("{}()方法发生[{}]异常,进行第{}次重试失败,异常信息:{}",methodName,errorClassName,retryCount,errorMessage);
    }
}
