//package com.atmb.adapter.consumer;
//
//
//import com.atmb.adapter.es.doc.RequestInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.KafkaListener;
//
//@Slf4j
//@Configuration
//public class RequestInfoConsumer extends SuperConsumer<RequestInfo>{
//
//    /**
//     * 索引后缀
//     */
//    public static String INDEX_SUFFIX = "_request";
//
//
//    @KafkaListener(topics = "request",groupId = "log-request")
//    public void listener(RequestInfo pojo) {
//        push(pojo, INDEX_SUFFIX);
//    }
//}
