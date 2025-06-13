package com.server.join;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@MapperScan("com.server.join.mapper")
@SpringBootApplication
public class JoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoinApplication.class, args);
        log.info("MyBatisPlusJoin测试模块启动完成");
    }

}
