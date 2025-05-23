package com.collect.annotation;

import com.collect.aspect.RestControllerAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 在配置类添加改注解,以启用Rest请求信息收集
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestControllerAspect.class)
public @interface EnableAutoRequestInfoCollect {
}
