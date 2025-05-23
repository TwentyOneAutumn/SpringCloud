package com.collect.annotation;

import com.collect.appender.LogCollectAppender;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 在配置类添加改注解,以启用日志信息收集
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogCollectAppender.class)
public @interface EnableAutoLogCollect {
}
