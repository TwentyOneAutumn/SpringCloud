package com.es.controller;

import cn.hutool.core.bean.BeanUtil;
import com.core.domain.Result;
import com.es.doc.RequestInfo;
import com.es.domain.RequestInfoDocumentSaveEntry;
import com.es.domain.RequestInfoIndexRecreatingEntry;
import com.es.service.IRequestInfoService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/request")
public class RequestInfoController {

    @Autowired
    private IRequestInfoService requestInfoService;


    /**
     * 重新创建索引
     */
    @PutMapping("/recreatingIndex")
    public Result indexRecreating(@Valid @RequestBody RequestInfoIndexRecreatingEntry entry) {
        return requestInfoService.indexRecreating(entry);
    }


    /**
     * 新增文档数据
     */
    @PostMapping("/saveDocument")
    public Result documentSave(@Valid @RequestBody RequestInfoDocumentSaveEntry entry) {
        return requestInfoService.documentSave(BeanUtil.toBean(entry, RequestInfo.class));
    }
}
