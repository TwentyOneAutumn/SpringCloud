package com.app.controller;

import com.core.domain.Build;
import com.core.domain.Result;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
public class TestController {

    @Autowired
    AppController appController;

    private final ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();

    @PostConstruct
    public void init() {
        threadPool.initialize();
    }


    @GetMapping("/log")
    public Result log() {
        log.info("测试消息");
        return Build.result(true);
    }
}
