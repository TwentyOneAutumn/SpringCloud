package com.kafka.consumer.listener;

import config.doMain.MessageInfo;
import config.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class kafkaMsgListener {

    @KafkaListener(topics = "SuperStarTopic",groupId = "SuperStarGroup")
    public void listenerMsg(String msg){
        MessageInfo message = JsonUtils.toMessage(msg);
        log.info(com.core.utils.JsonUtils.toJson(message));
    }
}
