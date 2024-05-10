package com.test;

import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {

    }

    public static void main(String[] args) throws IOException {
        // 给定的日期时间字符串
        String dateTimeString = "2024-05-07 09:54:16.000";

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // 解析字符串为 LocalDateTime 对象
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        // 输出结果
        System.out.println("转换后的 LocalDateTime 对象: " + dateTime);
    }
}