package com.core.utils;

import cn.hutool.core.bean.BeanUtil;

/**
 * 类型工具类
 */
public class TypeUtils {

    /**
     * 判断对象是否为基本类型
     * @param obj 对象
     * @param <T> 泛型
     * @return true:对象为基本类型，其中包括八种基本类型及其包装类以及String false:其他引用对象类型
     */
    public static <T> boolean isBasicType(T obj){
        return obj instanceof Byte ||
                obj instanceof Short ||
                obj instanceof Integer ||
                obj instanceof Long ||
                obj instanceof Float ||
                obj instanceof Double ||
                obj instanceof Boolean ||
                obj instanceof Character ||
                obj instanceof String;
    }

    /**
     *
     * @param obj 对象
     * @param clazz 目标对象类型
     * @param <T> 泛型
     * @param <E> 泛型
     * @return 转换后的对象
     */
    public static <T,E> E toTypeBean(T obj,Class<E> clazz){
        if(isBasicType(obj)){
           return (E)obj;
        }else {
            return BeanUtil.toBean(obj,clazz);
        }
    }
}
