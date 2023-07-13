package com.test;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.core.utils.SpringUtil;
import com.database.config.EnableBeanConfig;
import com.test.doMain.Test1;
import com.test.doMain.Test2;
import com.test.doMain.Test3;
import com.test.mapper.test1.Test1Mapper;
import com.test.mapper.test2.Test2Mapper;
import com.test.mapper.test3.Test3Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class NotNullTest {

    @Autowired
    Test1Mapper test1Mapper;
    @Autowired
    Test2Mapper test2Mapper;
    @Autowired
    Test3Mapper test3Mapper;

    @Test
    @Transactional
    public void test(){
        test1();
        test2();
        test3();
        log.info("测试成功");
    }

    public void test1(){
        test1Mapper.update(null,new LambdaUpdateWrapper<Test1>()
                .set(Test1::getName,"Test1")
                .eq(Test1::getId,"1")
        );
    }

    public void test2(){
        test2Mapper.update(null,new LambdaUpdateWrapper<Test2>()
                .set(Test2::getName,"Test2")
                .eq(Test2::getId,"1")
        );
    }

    public void test3(){
        test3Mapper.update(null,new LambdaUpdateWrapper<Test3>()
                .set(Test3::getName,"Test3")
                .eq(Test3::getId,"1")
        );
    }
}
