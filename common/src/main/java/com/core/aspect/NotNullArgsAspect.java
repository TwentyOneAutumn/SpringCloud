package com.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class NotNullArgsAspect {

    @Pointcut("@within(com.core.Interface.NotNullArgs) || @annotation(com.core.Interface.NotNullArgs) || execution(* *(.., @com.core.Interface.NotNullArgs (*), ..))")
    public void notNullArgs(){}

    /**
     * 通过切面匹配目标方法并进行拦截
     * 对参数进行非空校验
     * @param joinPoint 连接点对象
     * @exception IllegalArgumentException 如果方法参数为空则抛出异常
     */
    @Before("notNullArgs()")
    public void checkArgsNotNull(JoinPoint joinPoint) throws IllegalArgumentException{
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法对象
        Method method = methodSignature.getMethod();
        // 获取方法名称
        String methodName = method.getName();
        // 获取方法参数
        Parameter[] parameters = method.getParameters();
        // 获取方法参数列表
        Object[] args = joinPoint.getArgs();
        // 循环判断参数是否为空
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                throw new IllegalArgumentException("方法:[" + methodName + "]的[" + parameters[i].getName() + "]参数不能为空");
            }
        }
    }
}
