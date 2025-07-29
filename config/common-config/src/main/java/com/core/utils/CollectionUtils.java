package com.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionUtils {

    /**
     * 将一个List分割为多个指定长度的List
     * @param list 初始的List集合
     * @param size 要分割的长度
     * @return 分割后的List
     */
    public static <T> List<List<T>> partitionList(List<T> list, int size) {
        // 检查分割的块大小是否大于0
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        // 检查分割的块大小是否大于0
        if (CollUtil.isEmpty(list) || list.size() <= size) {
            return new ArrayList<>(10);
        }
        // 使用 IntStream 根据 size 计算出子列表的起始索引
        return IntStream.range(0, (list.size() + size - 1) / size)
                // 将每个起始索引映射为子列表
                .mapToObj(i -> list.subList(i * size, Math.min(size * (i + 1), list.size())))
                // 将流转换为 List<List<T>>
                .collect(Collectors.toList());
    }
}
