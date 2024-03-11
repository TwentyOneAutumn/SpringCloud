package com.test;

import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {
        
    }

    public static void main(String[] args) {

    }
}