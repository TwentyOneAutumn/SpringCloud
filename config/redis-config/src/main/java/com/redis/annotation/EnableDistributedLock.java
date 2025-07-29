package com.redis.annotation;

import com.redis.config.DistributedLockConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在配置类添加该注解,以启用分布式锁
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DistributedLockConfig.class)
public @interface EnableDistributedLock {
}
