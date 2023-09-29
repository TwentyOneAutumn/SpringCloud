package com.kafka.consumer.listener;

import config.doMain.MessageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class kafkaMsgListener {

    @Bean
    public Consumer<MessageInfo> SuperStarConsumer() {
        return message -> {
            log.info("接收的普通消息为：{}", message);
        };
    }
}
