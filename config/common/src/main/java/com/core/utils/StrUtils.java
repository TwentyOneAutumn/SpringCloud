package com.core.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * String工具类
 */
public class StrUtils {

    /**
     * 拼接多个字符
     * @param strings 字符参数
     * @return 拼接后的字符串
     */
    public static String join(String ... strings){
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * 拼接Map集合
     * @param map Map集合
     * @param keyValSymbol 拼接键值的字符
     * @param EntitySymbol 拼接元素的字符
     * @return 拼接后的字符串
     */
    public static String join(Map<String,String> map,String keyValSymbol,String EntitySymbol){
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            builder.append(key).append(keyValSymbol).append(value);
            if(iterator.hasNext()){
                builder.append(EntitySymbol);
            }
        }
        return builder.toString();
    }
}
