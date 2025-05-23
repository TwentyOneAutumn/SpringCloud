package com.es.service;

import com.core.domain.Result;
import com.es.doc.RequestInfo;
import com.es.domain.RequestInfoDocumentSaveEntry;
import com.es.domain.RequestInfoIndexRecreatingEntry;

public interface IRequestInfoService {

    /**
     * 重新创建索引
     */
    Result indexRecreating(RequestInfoIndexRecreatingEntry entry);

    /**
     * 新增文档数据
     */
    Result documentSave(RequestInfo entry);
}