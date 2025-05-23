package com.es.config;

public interface IndexMapper<T> {

    /**
     * 创建索引
     */
    void save(String indexName);


    /**
     * 删除索引
     */
    void delete(String indexName);


    /**
     * 重建索引
     */
    void Recreating(String indexName);
}
