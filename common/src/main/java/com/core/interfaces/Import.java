package com.core.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Import {

    /**
     * 字段映射名
     */
    String value();

    /**
     * 是否可以为空
     */
    boolean isNull() default true;

    /**
     * 如果非空字段为空抛出的错误信息
     */
    String errorMsg() default "有非空字段值为空";
}
