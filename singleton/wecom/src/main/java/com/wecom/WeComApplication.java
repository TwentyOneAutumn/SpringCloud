package com.wecom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableRetry
@SpringBootApplication
public class WeComApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeComApplication.class, args);
        log.info("企业微信对接服务启动成功");
    }
}
