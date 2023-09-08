package com.test;

import cn.hutool.core.util.StrUtil;
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
        String fileName = "context.txt";
        System.out.println(StrUtil.isNotBlank(fileName));
        System.out.println(fileName.contains("."));
        if(StrUtil.isNotBlank(fileName) && fileName.contains("\\.")){
            String[] split = fileName.split("\\.");
            System.out.println(split[1]);
        }
    }
}
