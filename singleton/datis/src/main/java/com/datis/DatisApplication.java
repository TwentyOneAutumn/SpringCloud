package com.datis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@MapperScan("com.datis.mapper")
@SpringBootApplication
public class DatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatisApplication.class, args);
        log.info("南宁数字通播引接程序启动成功");
    }

}
