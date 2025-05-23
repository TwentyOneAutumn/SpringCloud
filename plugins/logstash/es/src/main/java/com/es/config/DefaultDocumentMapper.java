package com.es.config;

import org.springframework.core.ResolvableType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class DefaultDocumentMapper<T> implements DocumentMapper<T> {

    ElasticsearchOperations operations;

    public DefaultDocumentMapper(ElasticsearchOperations operations) {
        this.operations = operations;
    }


    /**
     * 新增文档
     */
    @Override
    public void save(T object, String indexName) {
        operations.save(object, IndexCoordinates.of(indexName));
    }
}
