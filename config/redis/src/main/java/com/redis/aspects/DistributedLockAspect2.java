package com.redis.aspects;

import cn.hutool.core.bean.BeanUtil;
import com.redis.annotation.SyncLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import io.netty.channel.unix.DomainSocketAddress;

/**
 * 分布式锁切面类
 */
@Slf4j
@Aspect
public class DistributedLockAspect2 {

    private final RedissonClient redisson;

    private final RedisTemplate<String, String> redisTemplate;

    public DistributedLockAspect2(RedissonClient redisson, RedisTemplate<String, String> redisTemplate) {
        this.redisson = redisson;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 定义切点，拦截 @SyncLock 注解的方法
     */
    @Pointcut("@annotation(com.redis.annotation.SyncLock)")
    public void SyncLock() {
    }

    /**
     * 环绕通知
     */
    @Around(value = "SyncLock()")
    public Object syncLockAround(ProceedingJoinPoint point) {
        // 获取构建锁所需参数
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        SyncLock annotation = method.getAnnotation(SyncLock.class);
        String lockKey = annotation.value();
        long waitTime = annotation.waitTime();
        long leaseTime = annotation.leaseTime();
        TimeUnit unit = annotation.unit();

        // 创建公平锁
        RLock fairLock = redisson.getFairLock(lockKey);
        // 响应对象
        Object result = null;
        try {
            // 记录获取锁前的时间戳
            long startTime = System.currentTimeMillis();
            // 尝试获取锁
            if (fairLock.tryLock(waitTime, leaseTime, unit)) {
                // 记录获取锁后的时间戳
                long endTime = System.currentTimeMillis();
                // 从Redis获取当前终端所在部门正在执行当前切点方法的时间戳
                String currentTime = redisTemplate.opsForValue().get("dept");
                // 判断在获取锁的时间内有没有数据变更
                if (BeanUtil.isNotEmpty(currentTime) && Long.parseLong(currentTime) > startTime && Long.parseLong(currentTime) < endTime) {
                    // 如果有数据变更则返回错误信息
                    result = "有数据变更,本次请求失效";
                } else {
                    // 执行目标方法并获取返回结果
                    result = point.proceed();
                    // 执行目标方法但是没有释放锁之前记录执行时间戳
                    redisTemplate.opsForValue().set("dept", String.valueOf(System.currentTimeMillis()), 60,TimeUnit.SECONDS);
                }
            } else {
                log.error("get fair lock fail");
            }
        } catch (Throwable e) {
            log.error("SyncLockAround Error", e);
        } finally {
            // 检查当前线程是否持有该锁
            if (fairLock.isHeldByCurrentThread()) {
                // 释放锁
                fairLock.unlock();
            }
        }
        return result;
    }
}
