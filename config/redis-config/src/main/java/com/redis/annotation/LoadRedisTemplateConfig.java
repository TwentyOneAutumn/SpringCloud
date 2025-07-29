package com.redis.annotation;

import com.redis.config.RedisTemplateConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 在方法上添加该注解,以启用RedisTemplate配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisTemplateConfig.class})
public @interface LoadRedisTemplateConfig {
}
