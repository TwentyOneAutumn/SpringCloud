package com.demo.Core.DoMain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * 键值对对象
 * @param <K> 键泛型
 * @param <V> 值泛型
 */
@Data
@AllArgsConstructor
public class MapEntry<K,V> implements Map.Entry<K,V>{

    /**
     * 私有化构造方法
     */
    private MapEntry(){}

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
}
