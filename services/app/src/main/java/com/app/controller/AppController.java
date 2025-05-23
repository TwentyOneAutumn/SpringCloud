package com.app.controller;

import com.redis.annotation.SyncLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class AppController {

    @SyncLock(value = "test",waitTime = 30, leaseTime = 20)
    @GetMapping("/test1")
    public String test1() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "已执行:" + 1;
    }

    @SyncLock(value = "test",waitTime = 30, leaseTime = 20)
    @GetMapping("/test2")
    public String test2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "已执行:" + 2;
    }
}
