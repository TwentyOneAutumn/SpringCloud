package com.es.config;

import com.es.doc.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerConfig {

    @Autowired
    StreamBridge streamBridge;


    @Bean
    public Consumer<LogInfo> appender() {
        return message -> {
            log.info("接收的普通消息为：{}", message);
        };
    }
}
