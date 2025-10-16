package com.app;

import com.collect.annotation.EnableAutoRequestInfoCollect;
import com.xxl.job.core.context.XxlJobContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAutoRequestInfoCollect
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("测试服务启动成功");
    }
}