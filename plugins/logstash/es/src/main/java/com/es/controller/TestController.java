package com.es.controller;

import cn.hutool.core.bean.BeanUtil;
import com.core.domain.Build;
import com.core.domain.Result;
import com.es.doc.LogInfo;
import com.es.domain.LogInfoDocumentSaveEntry;
import com.es.domain.LogInfoIndexRecreatingEntry;
import com.es.service.ILogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping
public class TestController {

    @Autowired
    StreamBridge streamBridge;


    /**
     * 重新创建索引
     */
    @GetMapping("/test")
    public Result test() {

        return Build.result(true);
    }
}
