package com.kafka.publisher.controller;

import com.core.doMain.AjaxResult;
import config.doMain.MessageInfo;
import config.enums.TopicEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/send")
public class KafkaSendMsgController {

    @Autowired
    private StreamBridge streamBridge;

    @PostMapping("/msg")
    public AjaxResult sendMsg(@Valid @RequestBody MessageInfo message){
        // 如果你在调用 streamBridge.send 时遇到了与 org.json.JSONObject 相关的错误
        // 这可能是因为你在消息内容中使用了 JSON 对象，但 Spring Cloud Stream 需要将消息内容转换为字节数组或字符串
        // 你需要确保消息内容是可序列化的
        // Message<String> message = MessageBuilder.withPayload("这是一条消息").build();
        // boolean send = streamBridge.send(TopicEnum.SUPER_STAR, message);
        boolean send = streamBridge.send(TopicEnum.SUPER_STAR, message);
        return send ? AjaxResult.success("消息发送成功") : AjaxResult.error("消息发送失败");
    }
}
