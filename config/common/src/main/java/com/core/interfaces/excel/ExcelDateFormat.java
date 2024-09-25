package com.core.interfaces.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间格式
 * 添加在类上作用域为全局
 * 添加在字段上作用域为字段对应的列
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDateFormat {

    /**
     * 设置日期格式
     * 默认为 yyyy-MM-dd HH:mm:ss格式
     */
    String format() default "yyyy-MM-dd HH:mm:ss";
}
