package com.test;

import com.test.doMain.SysTest;
import com.test.mapper.SysTestMapper;
import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class TestDemo {


    @Test
    @Transactional
    public void test(){
        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime now2 = LocalDateTime.now();
        LocalDateTime localDateTime = now2.minusDays(1);
        long minutes = Duration.between(localDateTime,now1).toMinutes();
        System.out.println(minutes);
    }
}
