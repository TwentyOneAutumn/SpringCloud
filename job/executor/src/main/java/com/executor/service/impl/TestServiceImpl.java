package com.executor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.executor.domain.Test;
import com.executor.mapper.TestMapper;
import com.executor.service.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Override
    public void outWork(String jobParam) {

    }

    public void test(){
        throw new RuntimeException("测试");
    }
}
