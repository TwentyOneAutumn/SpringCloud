package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.doMain.SysTest;
import com.test.mapper.SysTestMapper;
import com.test.service.ISysTestService;
import org.springframework.stereotype.Service;

@Service
public class SysTestServiceImpl extends ServiceImpl<SysTestMapper, SysTest> implements ISysTestService {

}
