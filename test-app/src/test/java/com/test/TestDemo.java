package com.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import com.core.doMain.CallableEntity;
import com.core.utils.CallableUtil;
import com.test.doMain.SysTest;
import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {

    }
}
