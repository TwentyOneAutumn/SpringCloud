package com.atmb.adapter.consumer;


import com.atmb.adapter.es.doc.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Configuration
public class LogInfoConsumer extends SuperConsumer<LogInfo>{

    /**
     * 索引后缀
     */
    public static String INDEX_SUFFIX = "_log";



    @KafkaListener(topics = "appender",groupId = "log-appender")
    public void listener(String pojo) {
        log.info("log info : {}", pojo);
//        push(pojo, INDEX_SUFFIX);
    }
}
