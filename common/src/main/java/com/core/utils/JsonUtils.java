package com.core.utils;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.mutable.MutablePair;
import cn.hutool.json.JSONObject;
import com.core.doMain.Row;

/**
 * Json工具类
 */
public class JsonUtils {

    /**
     * 转换Json字符串为Bean
     * @param jsonStr Json字符串
     * @param tClass Bean类型
     * @param <T> 泛型
     * @return 转换后的Bean
     */
    public static <T> T toBean(String jsonStr,Class<T> tClass) {
        return new JSONObject(jsonStr).toBean(tClass);
    }

    public static String toJson(Object obj){
        return new JSONObject(obj).toString();
    }

    public static String toJson(Object obj,int indentFactor){
        return new JSONObject(obj).toJSONString(indentFactor);
    }

    public static String toJson(Object obj, int indentFactor, Filter<MutablePair<Object, Object>> filter){
        return new JSONObject(obj).toJSONString(indentFactor,filter);
    }
}
