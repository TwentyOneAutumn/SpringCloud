package com.kafka.publisher.controller;

import com.core.doMain.AjaxResult;
import config.doMain.MessageInfo;
import config.enums.TopicEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/send")
public class KafkaSendMsgController {

    @Autowired
    private StreamBridge streamBridge;

    @PostMapping("/msg")
    public AjaxResult sendMsg(@Valid @RequestBody MessageInfo message){
        boolean send = streamBridge.send(TopicEnum.SUPER_STAR, message);
        return send ? AjaxResult.success("消息发送成功") : AjaxResult.error("消息发送失败");
    }
}
