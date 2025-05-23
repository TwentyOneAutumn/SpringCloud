package com.collect.kafka;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.util.MimeType;

import java.io.Serializable;

public class LogSendClient {

    public static StreamBridge streamBridge;

    public static boolean isOpen = false;

    public LogSendClient(StreamBridge streamBridge) {
        LogSendClient.streamBridge = streamBridge;
    }

    public static <T extends Serializable> void send(String topic, T message){
        // 如果你在调用 streamBridge.send 时遇到了与 org.json.JSONObject 相关的错误
        // 这可能是因为你在消息内容中使用了 JSON 对象，但 Spring Cloud Stream 需要将消息内容转换为字节数组或字符串
        // 你需要确保消息内容是可序列化的
        // Message<String> message = MessageBuilder.withPayload("这是一条消息").build();
        // boolean send = streamBridge.send(TopicEnum.SUPER_STAR, message);
        try {
            if(isOpen){
                streamBridge.send(topic, message);
            }else {
                if(BeanUtil.isNotEmpty(streamBridge)){
                    isOpen = true;
                }
            }
        }catch (Exception e){
//            e.printStackTrace();
        }
    }
}
