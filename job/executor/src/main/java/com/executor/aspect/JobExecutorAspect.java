package com.executor.aspect;

import com.executor.domain.ExecutorState;
import com.executor.interfaces.Executor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class JobExecutorAspect {

    /**
     * 以 @Executor 注解标记的方法为切点
     */
    @Pointcut("@annotation(com.executor.interfaces.Executor)")
    public void executorAspect(){}

    /**
     * 任务方法环绕通知
     */
    @Around("executorAspect()")
    public Object executorAround(ProceedingJoinPoint joinPoint){
        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Executor executorAnnotation = method.getAnnotation(Executor.class);
        // 任务名称
        String name = executorAnnotation.name();
        // 任务频率
        String frequency = executorAnnotation.frequency();

        // 初始化任务状态
        ExecutorState executorState = ExecutorState.create(name, frequency);
        try {
            // 执行目标方法
            joinPoint.proceed();
        } catch (Throwable throwable) {
            // 错误信息
            String message = throwable.getMessage();
            // 获取栈信息
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            // 获取第一个
            StackTraceElement element = stackTrace[0];
            // 获取抛出异常的类名
            String className = element.getClassName();
            // 获取抛出异常的方法名
            String methodName = element.getMethodName();
            // 获取抛出异常的行号
            int lineNumber = element.getLineNumber();
            // 设置异常信息
            executorState.setError(className,methodName,lineNumber,message);
        }
        // 发送日志
        executorState.log();
        return null;
    }
}
