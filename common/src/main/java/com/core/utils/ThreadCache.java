package com.core.utils;

import cn.hutool.core.bean.BeanUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程缓存类
 */
public class ThreadCache {

    /**
     * 缓存Map
     * key:线程ID
     * value:缓存数据
     */
    private static final ConcurrentHashMap<Long,Object> cacheMap =  new ConcurrentHashMap<>();

    /**
     * 设置缓存
     * 如果缓存已存在则覆盖
     * @param value 缓存对象
     */
    public static void setCache(Object value){
        setCache(value,true);
    }

    /**
     * 设置缓存
     * @param isOverride 是否覆盖
     * @param value 缓存对象
     */
    public static void setCache(Object value,boolean isOverride){
        if(isOverride){
            cacheMap.put(Thread.currentThread().getId(),value);
        }else {
            if(!isCache()){
                cacheMap.put(Thread.currentThread().getId(),value);
            }
        }
    }

    /**
     * 获取缓存
     * 如果缓存不存在则返回null
     * @return 缓存对象
     */
    public static Object getCache(){
        if(isCache()){
            return cacheMap.get(Thread.currentThread().getId());
        }
        return null;
    }

    /**
     * 获取缓存并进行类型转换
     * @param type 类型
     * @return 转换后的对象
     */
    public static <T> T getCache(Class<T> type){
        Object cache = getCache();
        if(cache != null){
            return BeanUtil.toBean(cache,type);
        }
        return null;
    }

    /**
     * 判断当前线程是否已有缓存
     * @return true:已有缓存 false:无缓存
     */
    public static boolean isCache(){
        return cacheMap.containsKey(Thread.currentThread().getId());
    }

    /**
     * 删除当前线程缓存
     * @return 被删除的元素
     */
    public static Object removeCache(){
        if(isCache()){
            return cacheMap.remove(Thread.currentThread().getId());
        }
        return null;
    }

    /**
     * 删除当前线程的缓存
     * 如果被删除的元素不为空，则转换为对应类型并返回
     * @param type 类型
     * @return 被删除的元素
     */
    public static <T> T removeCache(Class<T> type){
        if(isCache()){
            Object cache = removeCache();
            if(cache != null){
                return BeanUtil.toBean(cache,type);
            }
        }
        return null;
    }
}
