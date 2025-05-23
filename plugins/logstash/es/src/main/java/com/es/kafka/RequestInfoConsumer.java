package com.es.kafka;


import com.es.doc.RequestInfo;
import com.es.service.IRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RequestInfoConsumer implements Consumer<RequestInfo> {
    
    @Autowired
    IRequestInfoService requestInfoService;

    /**
     * 监听Kafka队列
     */
    @Override
    public void accept(RequestInfo pojo) {
        requestInfoService.documentSave(pojo);
    }
}
