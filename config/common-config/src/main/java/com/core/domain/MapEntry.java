package com.core.domain;

import lombok.Data;

import java.util.Map;

/**
 * 键值对对象
 * @param <K> 键泛型
 * @param <V> 值泛型
 */
@Data
public class MapEntry<K,V> implements Map.Entry<K,V>{

    /**
     * 私有化构造方法
     */
    private MapEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    /**
     * 键
     */
    private K key;

    /**
     * 值
     */
    private V value;

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

    /**
     * 静态构建方法
     * @param key 键
     * @param value 值
     * @return MapEntry
     */
    public static <K,V> MapEntry<K,V> create(K key, V value){
        return new MapEntry<>(key,value);
    }
}
