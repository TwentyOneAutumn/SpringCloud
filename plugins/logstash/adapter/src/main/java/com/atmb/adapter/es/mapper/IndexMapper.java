package com.atmb.adapter.es.mapper;

public interface IndexMapper<T> {

    /**
     * 创建索引
     */
    void save(String indexName,Class<T> clazz);


    /**
     * 删除索引
     */
    void delete(String indexName);


    /**
     * 重建索引
     */
    void recreating(String indexName, Class<T> clazz);
}
