package com.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LocalDateTimeTestDemo {

    @Test
    public void LocalDateTimeTest(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime of = LocalDateTime.of(2022, 10, 25, 15, 30, 30);
        LocalDateTime parse = LocalDateTime.parse("2022-10-25T11:30:30");
        System.out.println(parse);
    }

}
