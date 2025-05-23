package com.udp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class UDPApplication {

    public static void main(String[] args) {
        SpringApplication.run(UDPApplication.class, args);
        log.info("UDP服务启动成功");
    }
}
