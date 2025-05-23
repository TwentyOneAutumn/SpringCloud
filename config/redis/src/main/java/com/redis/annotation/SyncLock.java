package com.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 在方法上添加该注解,以启用分布式锁AOP增强
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SyncLock {

    /**
     * 锁名称
     * 相同名称的锁之间会按照请求锁的先后顺序获取锁
     */
    String value();

    /**
     * 锁等待时间
     * 控制获取锁的超时时间，防止长时间等待
     */
    long waitTime();

    /**
     * 锁持有时间
     * 持有锁超过该时间,会自动释放锁,防止死锁
     * 如果值为`-1`则代表无限持有,直到锁的unlock()被显式调用
     */
    long leaseTime();

    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
