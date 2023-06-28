package com.test;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.core.doMain.Row;
import com.test.doMain.Test1;
import com.test.doMain.Test2;
import com.test.doMain.Test3;
import com.test.mapper.test1.Test1Mapper;
import com.test.mapper.test1.Test2Mapper;
import com.test.mapper.test1.Test3Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotNullTest {

    @Autowired
    Test1Mapper test1Mapper;
    @Autowired
    Test2Mapper test2Mapper;
    @Autowired
    Test3Mapper test3Mapper;

    @Test
    public void test(){
        test1();
        test2();
        test3();
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
        throw new RuntimeException();
    }

    public void test3(){
        test3Mapper.update(null,new LambdaUpdateWrapper<Test3>()
                .set(Test3::getName,"Test3")
                .eq(Test3::getId,"1")
        );
    }
}
