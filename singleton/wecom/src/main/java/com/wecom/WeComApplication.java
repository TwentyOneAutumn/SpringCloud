package com.wecom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class WeComApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeComApplication.class, args);
        log.info("企业微信服务启动成功");
    }
}
