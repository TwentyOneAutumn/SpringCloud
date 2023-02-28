package com.demo.Core.Utils;

import cn.hutool.core.bean.BeanUtil;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamUtils<E> {

    /**
     * 过滤集合
     * @param collection 集合对象
     * @param predicate 过滤器
     * @param <T> 泛型,继承Collection
     * @return 过滤后的集合
     */
    public <T extends Collection<E>> T filter(T collection, Predicate<E> predicate){
        return (T)collection.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @return 排序后的集合
     */
    public <T extends Collection<E>> T sorted(T collection,Comparator<E> comparator){
        return (T)collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @return 转换后的集合
     */
    public <T extends Collection<E>> T map(T collection, Class<?> clazz){
        return (T)collection.stream().map(v -> {
            try{
              return   BeanUtil.copyProperties(v,clazz);
            }catch (Exception ex){
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).collect(Collectors.toList());
    }

//    public void allMatch(){
//        boolean flg = this.list.stream().allMatch(order -> 2 == order.getOrderType());
//        System.out.println(flg);
//    }
//
//    public void anyMatch(){
//        boolean flg = this.list.stream().anyMatch(order -> 2 == order.getOrderType());
//        System.out.println(flg);
//    }
//
//    public void noneMatch(){
//        boolean flg = this.list.stream().noneMatch(order -> 2 == order.getOrderType());
//        System.out.println(flg);
//    }
//
//    public void findFirst(){
//        Optional<Order> first = this.list.stream().findFirst();
//        System.out.println(first.get());
//    }
//
//    public void findAny(){
//        Optional<Order> any = this.list.stream().findAny();
//        System.out.println(any.get());
//    }
//
//    public void count(){
//        long count = this.list.stream().count();
//        System.out.println(count);
//    }
//
//    public void max(){
//        Optional<Order> max = this.list.stream().max(Comparator.comparing(Order::getIsOrder));
//        System.out.println(max.get());
//    }
//
//    public void min(){
//        Optional<Order> min = this.list.stream().min(Comparator.comparing(Order::getIsOrder));
//        System.out.println(min.get());
//    }
//
//    public void groupingBy(){
//        Map<Integer, List<Order>> groupingByList = this.list.stream().collect(Collectors.groupingBy(Order::getIsOrder));
//        groupingByList.forEach((k, v) ->{
//            System.out.println(k + " ---> " + v);
//        });
//    }

}
