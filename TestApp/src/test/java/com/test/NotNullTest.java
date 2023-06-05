package com.test;

import com.core.utils.StreamUtils;
import com.test.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest
public class NotNullTest {

    @Test
    public void test(){
        ArrayList<Object> list = null;
        long l = StreamUtils.filterCount(list, item -> true);
        System.out.println("测试通过");
    }
}
