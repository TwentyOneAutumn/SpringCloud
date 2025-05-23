package com.es.service.impl;

import com.core.domain.Build;
import com.core.domain.Result;
import com.es.config.DocumentMapper;
import com.es.config.IndexMapper;
import com.es.doc.LogInfo;
import com.es.domain.LogInfoIndexRecreatingEntry;
import com.es.service.ILogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class LogInfoServiceImpl implements ILogInfoService {

    @Autowired
    private IndexMapper<LogInfo> indexMapper;

    @Autowired
    private DocumentMapper<LogInfo> documentMapper;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    /**
     * 重新创建索引
     */
    @Override
    public Result indexRecreating(LogInfoIndexRecreatingEntry entry) {
        indexMapper.Recreating(entry.getIndexName());
        return Build.result(true);
    }


    /**
     * 新增文档数据
     */
    @Override
    public Result documentSave(LogInfo entry) {
        String indexName = buildIndexName(entry.getServiceName());
        // 创建索引
        indexMapper.save(indexName);
        // 将文档写入索引
        documentMapper.save(entry, indexName);
        return Build.result(true);
    }


    /**
     * 构建索引名称
     */
    public String buildIndexName(String serviceName) {
        return serviceName + "_request_" + LocalDate.now().format(formatter);
    }
}
