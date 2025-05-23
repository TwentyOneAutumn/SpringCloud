package com.es.service;

import com.core.domain.Result;
import com.es.doc.LogInfo;
import com.es.domain.LogInfoIndexRecreatingEntry;

public interface ILogInfoService {

    /**
     * 重新创建索引
     */
    Result indexRecreating(LogInfoIndexRecreatingEntry entry);

    /**
     * 新增文档数据
     */
    Result documentSave(LogInfo entry);
}