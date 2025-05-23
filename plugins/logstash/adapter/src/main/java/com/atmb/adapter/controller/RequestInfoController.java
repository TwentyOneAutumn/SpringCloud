//package com.atmb.adapter.controller;
//
//import com.atmb.adapter.consumer.RequestInfoConsumer;
//import com.atmb.adapter.es.doc.RequestInfo;
//import com.atmb.adapter.es.domain.LogInfoIndexRecreatingEntry;
//import com.atmb.adapter.es.mapper.IndexMapper;
//import com.core.domain.Build;
//import com.core.domain.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@Slf4j
//@RestController
//@RequestMapping("/request")
//public class RequestInfoController {
//
//
//    @Autowired
//    private IndexMapper<RequestInfo> indexMapper;
//
//
//    /**
//     * 重新创建索引
//     */
//    @PutMapping("/recreatingIndex")
//    public Result indexRecreating(@Valid @RequestBody LogaInfoIndexRecreatingEntry entry) {
//        indexMapper.recreating(entry.getIndexName() + RequestInfoConsumer.INDEX_SUFFIX, RequestInfo.class);
//        return Build.result(true);
//    }
//}
