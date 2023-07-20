package com.activemq.consumer.listener;

import com.activemq.config.enums.TopicEnum;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqListener {

    @JmsListener(destination = TopicEnum.SUPER_STAR)
    public void ListenerMsg(String msg) {
        System.out.println("接收到ActiveMq消息：" + msg);
    }
}
