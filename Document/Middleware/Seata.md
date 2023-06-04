# Seata



### 简介



---



##### 分布式事务

在分布式系统下，一个业务跨越多个服务或者数据源，每个服务都是一个分支事务，要保证所有分支事务最总状态一致，这样的事务就是分布式事务



##### CAP定理

分布式系统有三个指标，且分布式系统无法同时满足这三个指标，这个结论就叫做CAP定理

Consistency ：一致性

Availability ：可用性

partition tolerance ：分区容错性

当分布式系统的各个节点通过网络连接，就一定会出现分区问题（P）

当分区出现时，系统的一致性和可用性无法同时满足，即CP或AP



##### BASE理论

BASE理论是对CAP的一种解决思路，有以下三种

Basically Available ：基本可用，分布式系统在出现故障时，允许损失部分可用性，保证核心可用

Soft State ：软状态，在一定事件内，允许出现中间状态，不如临时的状态不一致

Eventually Consistent ：最终一致性，虽然无法保证数据的强一致性，但是在软状态结束后最终达到数据一致



##### 解决方案

AP模式 ：各个子事务分别执行和提交，允许出现结果不一致，然后采用弥补措施恢复数据，实现最终一致

CP模式 ：各个子事务执行后互相等待，同时提交，同时回滚，达成强一致，但事务等待过程中，处于弱可用状态



---



### Seata术语



#### TC (Transaction Coordinator) - 事务协调者

维护全局和分支事务的状态，驱动全局事务提交或回滚

#### TM (Transaction Manager) - 事务管理器

定义全局事务的范围：开始全局事务、提交或回滚全局事务

#### RM (Resource Manager) - 资源管理器

管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚



---



### 依赖

```xml
<!-- SpringCloudAlibaba父依赖 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>${AlibabaCloud.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!-- Seata依赖 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <version>${AlibabaCloud.version}</version>
</dependency>
```



---



### XA模式



##### 简介

XA规范是 X/Open 组织顶的分布式事务处理( DTP，Distributed Transaction Processing )标准，XA规范表述了全局的TM与局部的RM之间的接口，几乎所有的主流数据库都对XA规范提供了支持



##### XA模式流程图

![image-20221122110801546](..\img\XA模式.png)



##### XA模式特点

1. XA模式是TC是根据各个微服务执行完毕后报告的事务状态，判断后进行统一提交或回滚，达到最终一致，底层是利用数据库底层支持来做统一的数据库事务处理
2. 强一致，满足ACID原则
3. 常用的数据库都支持，实现简单，并且没有代码入侵
4. 因为一阶段微服务之间需要互相等待阻塞，二阶段才会结束释放资源，会占用 线程 CPU 数据库锁 等资源，所以会产生资源消耗 性能较差等问题
5. 强依赖数据库底层的实现



##### 实现XA模式

1. 开启XA模式

   ```yaml
   seata: 
   	data-source-proxy-mode: XA
   ```

2. 给发起全局事务的入口方法添加@GlobalTransaction

   ```java
   @Override
   @GlobalTransaction
   public AjaxResult add(Dto dto) {
       boolean flg1 = UserService.add(dto);
       boolean flg2 = OrderService.add(dto);
       // 业务逻辑判断...
       return ...;
   }
   ```



---



### AT模式



##### 简介

AT模式同样是分阶段提交的事务模型，不过缺弥补了XA模型中资源锁定周期过长的缺陷



##### AT模式流程图

![image-20221122110801546](..\img\AT模式.png)



##### AT模式特点

1. XA模式第一阶段直接提交事务，不锁定资源，资源消耗小
2. XA模式依赖数据库机制实现回滚（利用数据快照回滚数据）
3. XA模式是最终一致
4. 没有代码侵入，由seata框架自动完成回滚和提交
5. 利用全局锁实现读写隔离，但是无法隔离非seata管理的全局事务产生的数据一致问题



### TCC模式



##### 简介

TCC模式与AT模式非常相似，每阶段都是独立事务，不同的是TCC通过人工编码来实现数据恢复



##### TCC模式流程图

![image-20221122110801546](..\img\TCC模式.png)



##### TCC模式特点

1. TCC模式有三个阶段

   ​	Try：资源检查和预留

   ​	Confirm：业务执行和提交

   ​	Cancel：预留资源的释放

2. TCC模式第一阶段直接提交事务，释放资源，性能较好

3. 相比于AT模式，无需生成数据快照，无需使用全局锁

4. 不依赖数据库事务，而是依赖补偿操作，可以用于非事务型数据库

5. 有代码侵入，需要人为编写 Try Confirm Cancel 接口，比较繁琐

6. 需要考虑Confirm和Cancel的失败情况，做好幂等处理

7. 最终一致





