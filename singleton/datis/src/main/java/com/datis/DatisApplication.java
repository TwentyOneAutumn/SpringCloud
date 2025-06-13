package com.datis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.datis.mapper")
@SpringBootApplication
public class DatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatisApplication.class, args);
    }

}
