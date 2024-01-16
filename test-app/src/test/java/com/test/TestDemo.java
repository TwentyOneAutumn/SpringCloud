package com.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class TestDemo {

//    @Autowired
//    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {
        String str = "111$222";
        String[] split = str.split("\\$");
        for (String s : split) {
            System.out.println(s);
        }
    }
}