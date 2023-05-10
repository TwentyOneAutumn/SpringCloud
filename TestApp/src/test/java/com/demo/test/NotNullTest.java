package com.demo.test;

import com.Core.Utils.StreamUtils;
import com.demo.test.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NotNullTest {

    @Test
    public void test(){
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("1",1,"1","1","1",1));
        orderList.add(new Order("1",1,"1","1","1",1));
        orderList.add(new Order("1",1,"1","1","1",1));
        orderList.add(new Order("1",1,"1","1","1",1));
        orderList.add(new Order("1",1,"1","1","1",1));
        orderList.add(new Order("1",1,"1","1","1",1));
        ArrayList<Object> list = new ArrayList<>();
        long l = StreamUtils.filterCount(list, item -> true);
        System.out.println("测试通过");
    }
}
