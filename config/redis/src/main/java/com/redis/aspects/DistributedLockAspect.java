//package com.redis.aspects;
//
//import com.redis.annotation.SyncLock;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.TimeUnit;
//
///**
// * 分布式锁切面类
// */
//@Slf4j
//@Aspect
//public class DistributedLockAspect {
//
//    private final RedissonClient redisson;
//
//    public DistributedLockAspect(RedissonClient redisson) {
//        this.redisson = redisson;
//    }
//
//    /**
//     * 定义切点，拦截 @SyncLock 注解的方法
//     */
//    @Pointcut("@annotation(com.redis.annotation.SyncLock)")
//    public void SyncLock() {}
//
//    /**
//     * 环绕通知
//     */
//    @Around(value = "SyncLock()")
//    public Object syncLockAround(ProceedingJoinPoint point) throws Throwable {
//        // 获取构建锁所需参数
//        MethodSignature methodSignature = (MethodSignature) point.getSignature();
//        Method method = methodSignature.getMethod();
//        SyncLock annotation = method.getAnnotation(SyncLock.class);
//        String lockKey = annotation.value();
//        long waitTime = annotation.waitTime();
//        long leaseTime = annotation.leaseTime();
//        TimeUnit unit = annotation.unit();
//
//        // 创建公平锁
//        RLock fairLock = redisson.getFairLock(lockKey);
//
//        Object result = null;
//        try {
//            // 尝试获取锁
//            if (fairLock.tryLock(waitTime, leaseTime, unit)) {
//                // 执行目标方法并获取返回结果
//                result = point.proceed();
//            } else {
//              log.error("get fair lock fail");
//            }
//        } catch (Exception e) {
//            log.error("SyncLockAround Error", e);
//        } finally {
//            // 检查当前线程是否持有该锁
//            if (fairLock.isHeldByCurrentThread()) {
//                // 释放锁
//                fairLock.unlock();
//            }
//        }
//        return result;
//    }
//}
