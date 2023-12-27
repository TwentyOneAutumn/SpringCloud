package com.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import reactor.util.retry.Retry;

/**
 * GatewayFilter切面类
 */
@Slf4j
//@Aspect
//@Component
public class GatewayFilterAspect {

    // 重试间隔时间(单位:秒)
    @Value(value = "${spring.cloud.gateway.filter.retry.interval:#{3}}")
    private Integer interval;

    // 最大重试次数
    @Value("${spring.cloud.gateway.filter.retry.retries:#{3}}")
    private Integer retries;

    /**
     * 切点
     */
    @Pointcut("execution(* org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory.apply(org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory.RetryConfig))")
    public void pointcut(){}

    /**
     * 环绕通知
     */
    @Around(value = "pointcut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行目标方法
        joinPoint.proceed();

        // 重写GatewayFilter,自定义重试逻辑
        return (GatewayFilter)(exchange, chain) -> chain.filter(exchange)
                // 记录日志
                .doOnError(throwable -> {
                    // 输出日志
                    log.error("异常原因：{}", throwable.getMessage());
                    // 休眠时间
                    try {
                        Thread.sleep(interval * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                // 使用 retryWhen 定义重试的条件和行为
                .retryWhen(Retry
                        // 指定最大的重试次数
                        .max(retries)
                        // 指定触发重试的条件
                        .filter(throwable -> true)
                );
    }
}
