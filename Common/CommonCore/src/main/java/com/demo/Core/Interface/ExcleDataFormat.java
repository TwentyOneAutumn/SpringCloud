package com.demo.Core.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcleDataFormat {

    /**
     * 设置日期格式
     * 默认为 yyyy-MM-dd HH:mm:ss格式
     */
    String format() default "yyyy-MM-dd HH:mm:ss";
}
