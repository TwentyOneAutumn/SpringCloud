package com.demo.Professional;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.demo.Professional.Mapper")
@EnableFeignClients(basePackages = {"com.school.api"})
@SpringBootApplication
public class ProfessionalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfessionalApplication.class, args);
    }

}
