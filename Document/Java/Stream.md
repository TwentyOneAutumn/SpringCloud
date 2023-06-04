# Stream



---



### 简介

Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。

Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象

Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码

这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等

元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果

Stream流一般用来操作集合中的元素，对元素进行增强，筛选，排序等操作



---



### 初始数据

```java
package com.demo.test.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Comparator;

/**
 * 订单类
 *
 * @author 第二十一个秋天
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Base implements Comparable<Order> {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单类型
     * 1:新建订单
     * 2:已付款
     * 3:过期订单
     */
    private Integer orderType;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 排序ID
     */
    private Integer isOrder;

    /**
     * 类比较器，根据序号比较
     * @param o 当前对象
     * @return [大于,等于,小于] : [正数,0,负数]
     */
    @Override
    public int compareTo(Order o) {
        return this.getIsOrder() - o.getIsOrder();
    }
}
```

```java
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
```



---



### filter过滤



> **用于过滤集合中不复合条件的元素**
>
> 
>
> ```java
> public void filter(){
>     List<Order> filterByOrderTypeList = this.list.stream().filter( order -> order.getOrderType() == 2).collect(Collectors.toList());
>     filterByOrderTypeList.forEach(System.out::println);
> }
> ```
>
> **过滤集合中orderType属性等于2的所有元素，丢弃不满足条件的元素**



---



### limit分页



> **用于获取流中指定数量的元素，从前往后开始获取**
>
> 
>
> ```java
> public void limit(){
>     List<Order> limitByOrderTypeList = this.list.stream().limit(10).collect(Collectors.toList());
>     limitByOrderTypeList.forEach(System.out::println);
> }
> ```
>
> **获取集合中前10个元素**



---



### skip分页



> **用于获取流中指定数量的元素，从后往前开始获取**
>
> 
>
> ```java
> public void limit(){
>     List<Order> limitByOrderTypeList = this.list.stream().skip(10).collect(Collectors.toList());
>     limitByOrderTypeList.forEach(System.out::println);
> }
> ```
>
> **获取集合中后10个元素**



---



### distinct去重



> **依据流中元素的equals()和hashCode()方法，对流中的元素进行去重**
>
> 
>
> ```java
> public void limit(){
>     List<Order> distinctByOrderTypeList = this.list.stream().distinct().collect(Collectors.toList());
>     distinctByOrderTypeList.forEach(System.out::println);
> }
> ```
>
> **对集合进行去重**



----



### sorted排序



> **调用Comparator.comparing()根据传入的排序字段进行比较，并返回一个Comparable比较器**
>
> **sorted()会根据传入的Comparable比较器的规则对元素进行比较和排序**
>
> **调用Comparator.comparing()进行单属性排序**
>
> **如果需要多属性排序则在后续调用Comparator.thenComparing()进行多属性排序**
>
> **会先进行第一排序，之后在保证第一排序顺序的情况下，进行第二排序，以此类推**
>
> **sorted()如果不传入外部比较器，则需要流中的元素实现Comparable<T>接口，并重写其compareTo(T)方法，用this比较方法形参，返回int值代表的结果为 ---> [大于,等于,小于] : [正数,0,负数]**
>
> **如果需要倒叙排序则可以在排序后调用Comparator.reversed()来设置所有条件为倒序排序**
>
> **如果需要单属性倒序，则在调用排序时传入Comparator.reverseOrder()**
>
> 
>
> ```java
> public void sorted(){
>     List<Order> sortedByOrderTypeList = this.list.stream()
>         .sorted(Comparator.comparing(Order::getIsOrder)
>                 .thenComparing(Order::getOrderType,Comparator.reverseOrder())
>                 .thenComparing(Order::getOrderId))
>         .collect(Collectors.toList());
>     sortedByOrderTypeList.forEach(System.out::println);
> }
> ```
>
> **根据元素的isOrder字段进行排序，之后根据orderType进行倒序排序，最后根据orderId进行排序**



---



### peek处理



> **对流中的元素进行操作，没有返回值**
>
> **可以对流中的元素做统一的处理，或设置统一的属性**
>
> 
>
> ```java
> public void peek(){
>     List<Order> peekSetOrderList = this.list.stream().peek(order -> order.setUserId("张三")).collect(Collectors.toList());
>     peekSetOrderList.forEach(System.out::println);
> }
> ```
>
> **将流中的Order对象的userId属性统一设置为张三**



---



### map映射



> **根据流中的元素，返回一个对象，之后stream的泛型将变为map返回的对象类型**
>
> **可以根据流中元素的信息，提取或转换出一个新的对象并返回**
>
> **例如转换List泛型，提取List中对象的单一属性等操作**
>
> 
>
> ```java
> public void map(){
>     List<OrderDto> mapOrderByDtoList = this.list.stream().map(order -> BeanUtil.copyProperties(order, OrderDto.class)).collect(Collectors.toList());
>     mapOrderByDtoList.forEach(System.out::println);
> }
> ```
>
> **根据List<Order>集合并对其元素进行操作，将其转换为List<OrderDto>集合**



---



### allMatch全部匹配



> **检查是否匹配所有元素**
>
> 
>
> ```java
> public void allMatch(){
>     boolean flg = this.list.stream().allMatch(order -> 2 == order.getOrderType());
>     System.out.println(flg);
> }
> ```
>
> **是否所有Order的orderType==2**



---



### anyMatch单个匹配



> **检查是否至少匹配一个元素**
>
> 
>
> ```java
> public void anyMatch(){
>     boolean flg = this.list.stream().anyMatch(order -> 2 == order.getOrderType());
>     System.out.println(flg);
> }
> ```
>
> **是否至少有一个Order的orderType==2**



---



### noneMatch无匹配



> **检查是否没有匹配所有元素**
>
> 
>
> ```java
> public void noneMatch(){
>     boolean flg = this.list.stream().noneMatch(order -> 2 == order.getOrderType());
>     System.out.println(flg);
> }
> ```
>
> **是否没有Order的orderType==2**



---



### findFirst第一个元素



> **返回第一个元素**
>
> 
>
> ```java
> public void noneMatch(){
>     boolean flg = this.list.stream().noneMatch(order -> 2 == order.getOrderType());
>     System.out.println(flg);
> }
> ```
>
> **返回第一个Order**



---



### findAny任意一个元素



> **返回任意一个元素**
>
> 
>
> ```java
> public void findAny(){
>     Optional<Order> any = this.list.stream().findAny();
>     System.out.println(any.get());
> }
> ```
>
> **返回任意一个Order**



---





### count统计



> **返回流中元素总数**
>
> 
>
> ```java
> public void count(){
>     long count = this.list.stream().count();
>     System.out.println(count);
> }
> ```
>
> **返回流中Order的总数**



---



### max最大



> **返回流中最大值**
>
> 
>
> ```java
> public void max(){
>     Optional<Order> max = this.list.stream().max(Comparator.comparing(Order::getIsOrder));
>     System.out.println(max.get());
> }
> ```
>
> **返回流中isOrder属性最大的Order**



---



### min最大



> **返回流中最小值**
>
> 
>
> ```java
> public void min(){
>     Optional<Order> min = this.list.stream().min(Comparator.comparing(Order::getIsOrder));
>     System.out.println(min.get());
> }
> ```
>
> **返回流中isOrder属性最小的Order**



---



### groupingBy分组



> **根据条件对流中元素进行分组**
>
> 
>
> ```java
> public void groupingBy(){
>     Map<Integer, List<Order>> groupingByList = this.list.stream().collect(Collectors.groupingBy(Order::getIsOrder));
>     groupingByList.forEach((k, v) ->{
>         System.out.println(k + " ---> " + v);
>     });
> }
> ```
>
> **输出分组后isOrder所对应的元素**



---

