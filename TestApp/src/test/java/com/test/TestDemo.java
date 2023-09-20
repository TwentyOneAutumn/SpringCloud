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
import java.util.ArrayList;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class TestDemo {


    @Test
    @Transactional
    public void test(){
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        Stream<String> concat = Stream.concat(list1.stream(), list2.stream().map(Object::toString));
    }
}
