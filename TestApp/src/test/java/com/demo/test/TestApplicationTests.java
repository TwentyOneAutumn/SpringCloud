package com.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {

    @Test
    void contextLoads() {
        String property = System.getProperty("user.dir") + "/src/log";
        System.out.println(property);
    }

}
