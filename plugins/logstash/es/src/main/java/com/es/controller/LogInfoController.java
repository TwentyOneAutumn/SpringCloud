package com.es.controller;

import cn.hutool.core.bean.BeanUtil;
import com.core.domain.Result;
import com.es.doc.LogInfo;
import com.es.domain.LogInfoDocumentSaveEntry;
import com.es.domain.LogInfoIndexRecreatingEntry;
import com.es.service.ILogInfoService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogInfoController {

    @Autowired
    private ILogInfoService logInfoService;


    /**
     * 重新创建索引
     */
    @PutMapping("/recreatingIndex")
    public Result indexRecreating(@Valid @RequestBody LogInfoIndexRecreatingEntry entry) {
        return logInfoService.indexRecreating(entry);
    }


    /**
     * 新增文档数据
     */
    @PostMapping("/saveDocument")
    public Result documentSave(@Valid @RequestBody LogInfoDocumentSaveEntry entry) {
        return logInfoService.documentSave(BeanUtil.toBean(entry, LogInfo.class));
    }
}
