package com.test;

import com.core.utils.SpringUtil;
import com.database.config.EnableBeanConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

@SpringBootTest
public class NotNullTest {

//    @Autowired
//    Test1Mapper test1Mapper;
//    @Autowired
//    Test2Mapper test2Mapper;
//    @Autowired
//    Test3Mapper test3Mapper;

    @Autowired
    DefaultSqlSessionFactory Test1SqlSessionFactory;

    @Test
//    @Transactional
    public void test(){
//        DefaultSqlSessionFactory test1SqlSessionFactory = SpringUtil.getBean("Test1SqlSessionFactory", DefaultSqlSessionFactory.class);
        System.out.println(Test1SqlSessionFactory);
        //        test1();
//        test2();
//        test3();
    }

//    public void test1(){
//        test1Mapper.update(null,new LambdaUpdateWrapper<Test1>()
//                .set(Test1::getName,"Test1")
//                .eq(Test1::getId,"1")
//        );
//    }
//
//    public void test2(){
//        test2Mapper.update(null,new LambdaUpdateWrapper<Test2>()
//                .set(Test2::getName,"Test2")
//                .eq(Test2::getId,"1")
//        );
////        throw new RuntimeException();
//    }
//
//    public void test3(){
//        test3Mapper.update(null,new LambdaUpdateWrapper<Test3>()
//                .set(Test3::getName,"Test3")
//                .eq(Test3::getId,"1")
//        );
//    }
}
