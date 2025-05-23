package com.collect.kafka;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class LogBeanConfig {

    @Bean
    public LogSendClient logSendClient(StreamBridge streamBridge) {
        return new LogSendClient(streamBridge);
    }
}
