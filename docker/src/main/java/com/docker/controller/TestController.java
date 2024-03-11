package com.docker.controller;

import com.docker.domain.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("/test")
    public Row<String> test(){
        redisTemplate.opsForValue().set("name","张三");
        String name = redisTemplate.opsForValue().get("name");
        return Row.success(name);
    }
}
