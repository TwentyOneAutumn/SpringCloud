package com.es.config;

import org.springframework.core.ResolvableType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class DefaultIndexMapper<T> implements IndexMapper<T> {

    ElasticsearchOperations operations;

    public DefaultIndexMapper(ElasticsearchOperations operations) {
        this.operations = operations;
    }


    /**
     * 获取泛型Class对象
     */
    @SuppressWarnings("unchecked")
    public Class<T> getTypeClass() {
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        return (Class<T>) resolvableType.getGeneric(0).resolve();
    }

    /**
     * 创建索引
     */
    public void save(String indexName) {
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
        IndexOperations indexOps = operations.indexOps(indexCoordinates);

        // 如果索引不存在,则创建索引
        if (!indexOps.exists()) {
            // 创建索引
            indexOps.create();
            // 将映射写入索引
            indexOps.putMapping(indexOps.createMapping(getTypeClass()));
        }
    }


    /**
     * 删除索引
     */
    @Override
    public void delete(String indexName) {
        IndexOperations indexOperations = operations.indexOps(IndexCoordinates.of(indexName));
        // 索引存在才删除
        if(indexOperations.exists()){
            indexOperations.delete();
        }
    }

    @Override
    public void Recreating(String indexName) {
        // 删除索引
        delete(indexName);
        // 创建索引
        save(indexName);
    }
}
