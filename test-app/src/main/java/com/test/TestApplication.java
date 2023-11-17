package com.test;

import com.database.multiDataSource.EnableMultiDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


//@MapperScan(basePackages = "com.test.mapper.test")
@SpringBootApplication
@EnableTransactionManagement
@EnableMultiDataSource
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
