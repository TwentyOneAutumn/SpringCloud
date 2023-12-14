package com.database.interfaces;

import com.database.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisConfig.class})
public @interface EnableRedisTemplate {
}
