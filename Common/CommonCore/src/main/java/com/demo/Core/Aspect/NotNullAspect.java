package com.demo.Core.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 判断参数是否为空
 */
@Aspect
@Component
public class NotNullAspect {

    /**
     * 前置通知，使用了com.demo.Core.Interface.NotNull注解的方法为切点
     * 拦截并验证参数是否为空，如果为空则抛出异常
     * @param joinPoint 连接点对象
     */
    @Before("@annotation(com.demo.Core.Interface.NotNull)")
    public void checkNotNullArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("Argument cannot be null!");
            }
        }
    }

}
