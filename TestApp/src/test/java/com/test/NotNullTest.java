package com.test;

import com.core.Interface.NotNullArgs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NotNullTest {

    @Test
    public void test(){
        NotNullTest.testNotNull(null);
    }

    public static void testNotNull(@NotNullArgs List list){
        System.out.println(list);
    }
}
