package com.app.controller;

import com.core.domain.Build;
import com.core.domain.Result;
import com.redis.topic.RedisTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
public class TestController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    @GetMapping("/sendToRedis")
    public Result sendToRedis() {
        int i = 0;
        while (i < 10) {
            i++;
            redisTemplate.convertAndSend(RedisTopic.INFORM, UUID.randomUUID().toString());
        }
        return Build.result(true);
    }
}
