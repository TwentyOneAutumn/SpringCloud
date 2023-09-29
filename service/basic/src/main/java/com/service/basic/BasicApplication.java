package com.service.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//@EnableScheduling
//@EnableAuthenticationPostProcessor
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"com.file.api"})
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
        log.info("Basic服务启动成功");
    }
}
