package com.test;

import cn.hutool.core.bean.BeanUtil;
import com.test.doMain.Test1;
import com.test.doMain.Test2;
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
        Test1 test1 = new Test1();
        test1.setTH("12312312");
        Test2 bean = BeanUtil.toBean(test1, Test2.class);
        System.out.println(bean);
    }
}