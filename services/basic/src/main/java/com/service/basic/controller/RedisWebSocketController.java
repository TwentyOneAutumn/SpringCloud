package com.service.basic.controller;

import com.alibaba.spring.beans.factory.annotation.AbstractAnnotationBeanPostProcessor;
import com.core.domain.Build;
import com.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisWebSocketController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send")
    public Result sendMessage(String message) {
        redisTemplate.convertAndSend("WebSocketTopic","测试发送消息");
        return Build.result(true);
    }
}
