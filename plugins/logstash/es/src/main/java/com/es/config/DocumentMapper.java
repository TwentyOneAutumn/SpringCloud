package com.es.config;

public interface DocumentMapper<T> {

    /**
     * 新增文档
     */
    void save(T object, String indexName);
}
