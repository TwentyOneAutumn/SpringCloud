//package com.es.kafka;
//
//
//import com.es.doc.LogInfo;
//import com.es.service.ILogInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.function.Consumer;
//
//@Slf4j
//@Component("appender")
//public class LogInfoConsumer implements Consumer<LogInfo> {
//
//    @Autowired
//    ILogInfoService logInfoService;
//
//    /**
//     * 监听Kafka队列
//     */
//    @Override
//    public void accept(LogInfo pojo) {
//        log.info("接受消息:{}}",pojo);
////        logInfoService.documentSave(pojo);
//    }
//}
