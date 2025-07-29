package com.core.excel.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格宽度
 * 添加在类上作用域为全局
 * 添加在字段上作用域为字段对应的列
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Width {

    /**
     * 设置单元格宽度
     */
    int width();
}
