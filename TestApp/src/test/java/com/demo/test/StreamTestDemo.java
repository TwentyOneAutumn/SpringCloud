package com.demo.test;

import cn.hutool.core.bean.BeanUtil;
import com.demo.test.pojo.Order;
import com.demo.test.pojo.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class StreamTestDemo {

    List<Order> list = init();

    public List<Order> init(){
        Order order1 = new Order("1001",1,"C0001","无","张三",1);
        Order order2 = new Order("1002",2,"C0002","有","李四",3);
        Order order3 = new Order("1003",3,"C0003","无","王五",2);
        Order order4 = new Order("1004",2,"C0004","有","赵六",4);
        Order order5 = new Order("1005",3,"C0005","无","孙七",5);
        Order order6 = new Order("1006",1,"C0006","有","刘八",7);
        Order order7 = new Order("1007",2,"C0007","无","周九",6);
        List<Order> list = new ArrayList<>();
        list.add(order1);
        list.add(order2);
        list.add(order3);
        list.add(order4);
        list.add(order5);
        list.add(order6);
        list.add(order7);
        return list;
    }

    @Test
    public void filter(){
        List<Order> filterByOrderTypeList = this.list.stream().filter( order -> order.getOrderType() == 2).collect(Collectors.toList());
        filterByOrderTypeList.forEach(System.out::println);
    }

    @Test
    public void sorted(){
        List<Order> sortedByOrderTypeList = this.list.stream()
                .sorted((Comparator.comparing(Order::getIsOrder)
                        .thenComparing(Order::getUserId)))
                .collect(Collectors.toList());
        sortedByOrderTypeList.forEach(System.out::println);
    }

    @Test
    public void peek(){
        List<Order> peekSetOrderList = this.list.stream().peek(order -> order.setUserId("张三")).collect(Collectors.toList());
        peekSetOrderList.forEach(System.out::println);
    }

    @Test
    public void map(){
        List<OrderDto> mapOrderByDtoList = this.list.stream().map(order -> BeanUtil.copyProperties(order, OrderDto.class)).collect(Collectors.toList());
        mapOrderByDtoList.forEach(System.out::println);
    }

    @Test
    public void allMatch(){
        boolean flg = this.list.stream().allMatch(order -> 2 == order.getOrderType());
        System.out.println(flg);
    }

    @Test
    public void anyMatch(){
        boolean flg = this.list.stream().anyMatch(order -> 2 == order.getOrderType());
        System.out.println(flg);
    }

    @Test
    public void noneMatch(){
        boolean flg = this.list.stream().noneMatch(order -> 2 == order.getOrderType());
        System.out.println(flg);
    }

    @Test
    public void findFirst(){
        Optional<Order> first = this.list.stream().findFirst();
        System.out.println(first.get());
    }

    @Test
    public void findAny(){
        Optional<Order> any = this.list.stream().findAny();
        System.out.println(any.get());
    }

    @Test
    public void count(){
        long count = this.list.stream().count();
        System.out.println(count);
    }

    @Test
    public void max(){
        Optional<Order> max = this.list.stream().max(Comparator.comparing(Order::getIsOrder));
        System.out.println(max.get());
    }

    @Test
    public void min(){
        Optional<Order> min = this.list.stream().min(Comparator.comparing(Order::getIsOrder));
        System.out.println(min.get());
    }

    @Test
    public void groupingBy(){
        Map<Integer, List<Order>> groupingByList = this.list.stream().collect(Collectors.groupingBy(Order::getIsOrder));
        groupingByList.forEach((k, v) ->{
            System.out.println(k + " ---> " + v);
        });
    }
}
