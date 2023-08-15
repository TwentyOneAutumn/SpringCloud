package com.test;

import com.test.doMain.SysTest;
import com.test.mapper.SysTestMapper;
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
    public void test(){
        SysTest sysTest = new SysTest();
        sysTest.setId("1");
        sysTest.setCode("test");
        sysTest.setAge(18);
        try {
            sysTestService.save(sysTest);
            sysTestService.save(sysTest);
        }
        catch (Exception ex){
            log.error("测试异常:{}",ex.getMessage());
        }
        log.info("测试成功");
    }
}
