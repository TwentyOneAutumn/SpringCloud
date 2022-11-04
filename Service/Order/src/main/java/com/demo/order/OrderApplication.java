package com.demo.order;

import com.demo.feign.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
// 向Spring容器注入Bean
@EnableFeignClients(clients = {UserClient.class})
// Mybatis 包扫描
@MapperScan("com.demo.order")
@SpringBootApplication
// 读取JDBC配置信息
@PropertySource(value = {"classpath:jdbc.properties"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}