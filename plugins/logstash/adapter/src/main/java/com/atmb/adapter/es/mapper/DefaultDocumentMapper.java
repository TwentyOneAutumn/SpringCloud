package com.atmb.adapter.es.mapper;

import cn.hutool.core.bean.BeanUtil;
import com.atmb.adapter.es.doc.SuperInfo;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Component
public class DefaultDocumentMapper<T extends SuperInfo> implements DocumentMapper<T> {

    ElasticsearchOperations operations;

    public DefaultDocumentMapper(ElasticsearchOperations operations) {
        this.operations = operations;
    }


    /**
     * 新增文档
     */
    @Override
    public void save(T object, String indexName) {
        if(BeanUtil.isNotEmpty(object)){
            operations.save(object, IndexCoordinates.of(indexName));
        }
    }
}
