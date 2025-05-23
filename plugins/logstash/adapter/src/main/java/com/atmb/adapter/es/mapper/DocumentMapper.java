package com.atmb.adapter.es.mapper;

import com.atmb.adapter.es.doc.SuperInfo;

public interface DocumentMapper<T extends SuperInfo> {

    /**
     * 新增文档
     */
    void save(T object, String indexName);
}
