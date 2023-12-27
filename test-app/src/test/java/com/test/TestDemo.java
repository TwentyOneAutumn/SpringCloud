package com.test;

import com.test.doMain.SysTest;
import com.test.doMain.SysTest2;
import com.test.mapper.test.SysTestMapper;
import com.test.mapper.test2.SysTest2Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
public class TestDemo {

//    @Autowired
//    private ISysTestService sysTestService;

    @Autowired
    private SysTest2Mapper sysTest2Mapper;
    @Autowired
    private SysTestMapper sysTestMapper;



    @Test
    @Transactional
    public void test() throws Exception {
        List<SysTest> sysTests = sysTestMapper.selectList(null);
        System.out.println(sysTests);
        List<SysTest2> sysTest2s = sysTest2Mapper.selectList(null);
        System.out.println(sysTest2s);
    }
}
