# Spring重点知识



# IOC

-----



### IOC说明

**控制反转**：把对象创建和对象之间调用过程交给Spring进行管理，通过反射实现

**目的**：为了降低耦合度，具体采用的方法就是所谓的依赖注入



-----



### **IOC实现流程（SpringBean的生命周期）**

![Bean的生命周期图](..\img\Spring\Bean的生命周期图.png)

1. **初始化BeanFactory**(Bean工厂)用来实例化Bean，底层进行了判断，如果Bean工厂存在，则销毁该工厂，并重新初始化，初始化出来的Bean工厂为空，并没有设置任何信息
2. **读取配置信息**：通过BeanDefintionReader读取例如XML配置文件，注解配置等，将Bean定义信息转化为BeanDefinition对象，然后将BeanDefinition对象加载进Bean工厂，有了Bean的定义信息后，对Bean工厂进行一系列设置
3. 然后执行**BeanFactoryPostProcessors**(增强操作)
4. **创建Bean的空对象(实例化Bean)**：BeanFactory通过反射机制来创建Bean的空对象，通过**Class.getConstructor()**来获取Bean的构造器对象：Constructor  ctor，再通过**ctor.newInstance()**来获取Bean的空对象
5. **进行依赖注入**：实例化后的Bean对象被封装在**BeanWrapper**对象中，之后Spring根据**BeanDefinition**中的信息以及通过对BeanWrapper提供的设置属性的接口来完成对Bean对象的属性的填充
6. **处理Aware接口**：Spring会检测该对象是否实现了xxxAware接口，并将相关的xxxAware实例注入给Bean，然后执行xxxAware所对应的Aware方法
7. **进行前置增强**：**applyBeanPostProcessorsBeforInitialization**
8. 进行Init(初始化)操作
9. **进行后置增强**：**applyBeanPostProcessorsAfterInitialization**
10. **创建出完整的可用对象**
11. **DisposableBean**：当Bean不再需要时，会经过清理阶段，如果Bean实现了DisposableBean这个接口，会调用其实现的destroy()方法，最后，如果这个Bean的Spring配置中配置了destroy-method属性，会自动调用其配置的销毁方法



------



### 依赖注入

Spring可以通过依赖注入获取Spring容器中的Bean，不需要自己去通过new或者反射去手动创建对象，这些对象在声明为Bean后会由Spring容器去创建，并注入到依赖该对象的地方

依赖注入的前提条件：

1. 依赖注入的Bean必须存在于Spring中
2. 需要依赖注入的类必须被@ComponentScan注解扫描到，才能实现自动装配
3. 在类中声明需要注入的Bean，并在Bean上加上@Autowired或@Resource

依赖注入所涉及到的三个注解：

**@ComponentScan**

作用：在Spring配置类上添加@ComponentScan注解，默认扫描配置类所在包及其子包下所有类，扫描到有@Autowired或@Resource注解，就会在IOC容器中自动查找需要的Bean，并自动装配给该对象的属性

**@Autowired**

作用：注入Bean对象，默认根据类型查找，如果找到了多个该类型的Bean，则会根据名称再次查找，如果上述查找结果为空，则会抛出异常，可以使用required=false来解决，required：是否必须注入该对象，如果设置为false，则在容器中找不到相应的Bean对象时，则不会注入，最后该对象为Null，@Autowired是Spring注解

**@Resource**

作用：注入Bean对象，默认根据名称注入，如果按照名称找不到匹配的Bean时，才会按照类型去查找，@Resource是JDK注解



------



# AOP

-----



### 动态代理

Spring AOP使用的动态代理，所谓的动态代理就是说AOP框架不会去修改字节码，而是在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法

Spring AOP中的动态代理主要有两种方式，JDK动态代理和CGLIB动态代理



------



### **JDK动态代理**

原理：通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口，JDK动态代理的核心是InvocationHandler接口和Proxy类

1. JDK动态代理是面向接口的，需要被增强类实现接口
2. 只能增强接口中定义且被被代理类重写的方法，被代理中其他方法无法被增强
3. 只能获取接中定义方法上的注解，不能读到实现类重写的方法上的注解



##### JDK动态代理流程

1. AOP通过反射获取目标类实现的所有接口
2. 调用Proxy的newProxyInstance()方法，传入目标类的类加载器，要代理的接口，InvocationHandler(要调用的处理器)，并在invoke()方法中动态的对目标方法进行织入增强
3. 通过调用InvocationHandler中的invoke()方法中Method对象的invoke方法，传入被代理对象和参数列表，回调目标方法



------



### **CGLIB动态代理**

原理：在程序运行期间动态的创建一个被代理类的子类并去继承被代理类，重写被代理类中所有非私有和fianl修饰的方法，然后在代理类中拦截被代理类中所有方法的调用并对其做出增强，从而实现动态代理

1. CGLIB动态代理是面向父类的，和接口没有直接关系
2. 可以增强被增强类中所有可以被子类重写的方法
3. 可以通过反射的方式获取被增强方法上的所有注解



##### CGLIB动态代理流程

1. 会在程序运行期间，创建一个代理类，并去继承目标类，重写目标类中所有非fianl和私有方法
2. 然后会去通过增强器Enhancer去根据传入的代理类目标类和设置的MethodInterceptor拦截器和增强逻辑去缓存代理类，并设置拦截器到代理类的TheardLoad属性中
3. 然后生成代理类的对象
4. 执行代理类对象重写的目标方法时，会从TheardLoad属性中获取拦截器MethodInterceptor
5. 调用MethodInterceptor中的intercept()方法，传入代理对象，目标对象，目标方法对象，目标方法参数对象
6. 在intercept()方法执行设置好的增强逻辑，并调用invoke()方法，传入目标对象和参数，来回调目标类方法
7. 然后在调用目标方法时，AOP底层就会对其进行拦截，转而执行代理类中的目标方法





------



### AOP术语

| 术语               | 说明                                         |
| ------------------ | -------------------------------------------- |
| 通知(Advice)       | 在切面中定义的增强目标方法的功能             |
| 连接点(JoinPoint)  | 目标类中可以被增强的方法称为连接点           |
| 切入点(Pointcut)   | 被增强的方法叫做切入点                       |
| 切面(Aspect)       | 由切点和通知组成，需要在切面中定义切点和通知 |
| 引入(introduction) | 为一个类添加的新字段或方法定义               |
| 目标类(target)     | 被增强的类                                   |
| 代理类(proxy)      | 被增强类的代理对象                           |
| 织入(weaving)      | 被增强方法编译执行并被增强的过程叫做织入     |



-----



### AOP通知

| 通知                      | 说明                                                      |
| ------------------------- | --------------------------------------------------------- |
| Before(前置通知)          | 在连接点前执行                                            |
| After(后置通知)           | 在连接点后执行，无论是否出现异常都会执行                  |
| Around(环绕通知)          | 在连接点前后都执行，比Before前，比After后                 |
| AfterThrowing(异常通知)   | 发生异常才会触发异常通知                                  |
| AfterReturning (返回通知) | 连接点方法返回后触发的返回通知，比After前，发生异常不触发 |



------



### AOP切点表达式

execution( [权限修饰符]  [需要增强的包] . [包下需要增强的类] (类参数) )

可以使用*作为通配符来对切点进行匹配



------



### AOP通知定义

```java
package com.demo.order.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class logAspect {

    /**
     * 定义一个公共的切点，可以通过方法名引用
     */
    @Pointcut("execution(* com.demo.*.*(*))")
    public void commonPointcut(){}

    /**
     * 返回通知
     * @param joinPoint 被增强方法的信息封装对象
     */
    @Before("commonPointcut()")
    public void BeforeAdvice(JoinPoint joinPoint){
        System.out.println("Before Advice Run ... ...");
    }

    /**
     * 返回通知
     * @param joinPoint 被增强方法的信息封装对象
     */
    @After("execution(* com.demo.*.*(*))")
    public void AfterAdvice(JoinPoint joinPoint){
        System.out.println("After Advice Run ... ...");
    }

    /**
     * 返回通知
     * @param proceedingJoinPoint 被增强方法的封装对象
     */
    @Around("execution(* com.demo.*.*(*))")
    public Object AroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Around Before Advice Run ... ...");
        // 切点方法执行
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("Around After Advice Run ... ...");
        return proceed;
    }

    /**
     * 返回通知
     * @param joinPoint 被增强方法的信息封装对象
     * @param returns 被增强方法的返回值
     */
    @AfterReturning(value = "execution(* com.demo.*.*(*))",returning = "returns")
    public void AfterReturningAdvice(JoinPoint joinPoint,Object returns){
        System.out.println("AfterReturning Advice Run ... ...");
    }

    /**
     * 异常通知
     * @param exception 被增强方法发生的异常
     */
    @AfterThrowing(value = "execution(* com.demo.*.*(*))",throwing = "exception")
    public void AfterThrowingAdvice(Exception exception){
        System.out.println("AfterThrowing Advice Run ... ...");
    }
}
```

当有多个切面对同一个方法进行增强时，可以通过@Order(int)注解来指定顺序，数字越小，优先级越高、

使用AOP需要在配置类上添加@EnableAspectJAutoProxy(proxyTargetClass = true)注解开启Spring对AOP的支持



-----



# 事务

-----



### 事务的四大特性（ACID）

| 特性                  | 说明                                                         |
| --------------------- | ------------------------------------------------------------ |
| 原子性（Atomicity）   | 事务是一个原子操作，事务中的全部操作在数据库中是不可分割的，要么全部完成，要么均不执行 |
| 一致性（Consistency） | 几个并行执行的事务，其执行结果必须与按某一顺序串行执行的结果相一致 |
| 隔离性（Isolation）   | 事务的执行不受其他事务的干扰，事务执行的中间结果对其他事务必须是透明的，需要事务隔离级别来指定隔离性 |
| 持久性（Durability）  | 对于任意已提交事务，系统必须保证该事务对数据库的改变不被丢失，即使数据库出现故障 |



-----



### 事务问题

| 问题       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| 脏读       | 一个事务读到另一个事务未提交的更新数据                       |
| 不可重复读 | 一个事务两次读同一行数据，有另一个事务操作了这条数据，导致这两次读到的数据不一样 |
| 幻读       | 一个事务执行两次查询，第一次查询执行完毕，然后另一个事务插入了一条数据，第二次查询比第一次查询多出了一行另一个事务插入的数据 |
| 丢失更新   | 撤消回滚一个事务时，把其它事务已提交的更新的数据覆盖了       |



-----



### 事务隔离级别

| 隔离级别        | 脏读 | 不可重复读 | 幻读 |
| --------------- | ---- | ---------- | ---- |
| ReadUnCommitTed | ❌    | ❌          | ❌    |
| ReadCommitTed   | ✔    | ❌          | ❌    |
| RepeatabLeread  | ✔    | ✔          | ❌    |
| Serialzable     | ✔    | ✔          | ✔    |

-----



### Spring事务

1. 配置事务管理器

   ```java
   package com.demo.order.config;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.jdbc.datasource.DataSourceTransactionManager;
   import javax.sql.DataSource;
   
   @Configuration
   public class orderBeanConfiguration {
   
       @Autowired
       private DataSource dataSource;
   
       @Bean
       public DataSourceTransactionManager dataSourceTransactionManager(){
           return new DataSourceTransactionManager(dataSource);
       }
   }
   
   ```

2. 在需要事务的方法上添加@Transactional()注解，开启事务

   

-----



### @Transactional注解配置

**propagation**：传播行为

| 事务的传播行为类型        | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 如果当前没有事务，就新建一个事务，如果已经存在一个事务，就加入到这个事务中（**默认选择**） |
| PROPAGATION_SUPPORTS      | 支持当前事务，如果当前没有事务，就以非事务的方式运行         |
| PROPAGATION_MANDATORY     | 使用当前事务，如果当前没有事务，就抛出异常                   |
| PROPAGATION_REQUIRES_NEW  | 新建事务，如果当前存在事务，就把当前事务挂起                 |
| PROPAGATION_NOT_SUPPORTED | 以非事务的方式执行操作，如果当前存在事务，就把当前事务挂起   |
| PROPAGATION_NEVER         | 以非事务的方式执行，如果当前存在事务，则抛出异常             |
| PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作 |



**Isolation**：隔离级别

| 事务的隔离级别             | 脏读 | 不可重复读 | 幻读 |
| -------------------------- | ---- | ---------- | ---- |
| DEFAULT                    | ⭕    | ⭕          | ⭕    |
| READ_UNCOMMITTED(读未提交) | ❌    | ❌          | ❌    |
| READ_COMMITTED(读已提交)   | ✔    | ❌          | ❌    |
| REPEATABLE_READ(可重复读)  | ✔    | ✔          | ❌    |
| SERIALIZABLE(串行化)       | ✔    | ✔          | ✔    |



**timeout**：超时时间

控制当前事务一定要在多长时间内提交，如果不提交就会回滚



**readOnly**：只读事务

事务是否只能读取数据库的数据，如果为true，则不允许进行增删改



**rollbackFor**：指定发生回滚的异常

当方法发生那些异常时才回滚



**noRollbackFor**：指定不发生回滚的异常

当方法发生那些异常时才回滚



-----



### Spring容器中Bean的作用域

| 作用域      | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| singleton   | 在SpringIOC容器仅存在一个Bean实例，Bean以单例方式存在，Bean作用域范围的默认值，创建Spring容器时IOC就会去创建singleton作用域的单例Bean |
| prototype   | 每次从容器中调用或请求Bean时，容器都会调用getBean()方法，返回一个新的Bean实例，创建Spring容器时，并没有创建作用域为prototype的Bean，会在请求该Bean的时候创建 |
| request     | 每次HTTP请求都会创建一个新的Bean，该作用域仅适用于web的Spring WebApplicationContext环境 |
| session     | 同一个HTTP Session共享一个Bean，不同Session使用不同的Bean。该作用域仅适用于web的Spring WebApplicationContext环境 |
| application | 限定一个Bean的作用域为`ServletContext`的生命周期。该作用域仅适用于web的Spring WebApplicationContext环境 |



-----



### Spring框架中的单例Bean是线程安全的

Spring框架并没有对单例bean进行任何多线程的封装处理，关于单例bean的线程安全和并发问题需要开发者自行去搞定

但实际上，大部分的Spring bean并没有可变的状态(比如Serview类和DAO类)，所以在某种程度上说Spring的单例bean是线程安全的

如果你的bean有多种状态的话（比如 View Model 对象），就需要自行保证线程安全，最浅显的解决办法就是将多态bean的作用域由“**singleton**”变更为“**prototype**”



-----



### SpringBean如何处理线程并发问题

在一般情况下，只有无状态的Bean才可以在多线程环境下共享，在Spring中，绝大部分Bean都可以声明为singleton作用域，因为Spring对一些Bean中非线程安全状态采用ThreadLocal进行处理，解决线程安全问题。

ThreadLocal和线程同步机制都是为了解决多线程中相同变量的访问冲突问题。同步机制采用了“时间换空间”的方式，仅提供一份变量，不同的线程在访问前需要获取锁，没获得锁的线程则需要排队。而ThreadLocal采用了“空间换时间”的方式。

ThreadLocal会为每一个线程提供一个独立的变量副本，从而隔离了多个线程对数据的访问冲突。因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。ThreadLocal提供了线程安全的共享对象，在编写多线程代码时，可以把不安全的变量封装进ThreadLocal



-----



### BeanFactory和ApplicationContext有什么区别

BeanFactory和ApplicationContext是Spring的两大核心接口，都可以当做Spring的容器。其中ApplicationContext是BeanFactory的子接口。

**BeanFactory**：是Spring里面最底层的接口，包含了各种Bean的定义，读取bean配置文档，管理bean的加载、实例化，控制bean的生命周期，维护bean之间的依赖关系

**ApplicationContext**：作为BeanFactory的派生，除了提供BeanFactory所具有的功能外，还提供了更完整的框架功能：

1) 继承MessageSource，因此支持国际化
2) 统一的资源文件访问方式
3) 提供在监听器中注册bean的事件
4) 同时加载多个配置文件
5) 载入多个（有继承关系）上下文 ，使得每一个上下文都专注于一个特定的层次，比如应用的web层



-----



### **BeanFactory和ApplicationContext注入Bean方式的差异**

**BeanFactroy**：BeanFactroy采用的是延迟加载形式来注入Bean的，即只有在使用到某个Bean时(调用getBean())，才对该Bean进行加载实例化。这样，我们就不能发现一些存在的Spring的配置问题。如果Bean的某一个属性没有注入，BeanFacotry加载后，直至第一次使用调用getBean方法才会抛出异常

**ApplicationContext**：它是在容器启动时，一次性创建了所有的Bean。这样，在容器启动时，我们就可以发现Spring中存在的配置错误，这样有利于检查所依赖属性是否注入。 ApplicationContext启动后预载入所有的单实例Bean，通过预载入单实例bean ,确保当你需要的时候，你就不用等待，因为它们已经创建好了

  	

-----



### Spring框架中都用到了哪些设计模式

**工厂模式**：BeanFactory就是简单工厂模式的体现，用来创建对象的实例

**单例模式**：Bean默认为单例模式，即在Spring容器中默认只有一个独立唯一的对象，减少了Bean实例的创建和回收，除了容器中第一次初始化Bean，后续Bean的获取都是从缓存中获取的，所以SpringBean设置为单例模式就是为了提高性能，劣势为如果Bean有多种状态的话在并发环境下线程不安全，最简单的方法是将Bean设置成多例模式(prototype)来解决

**代理模式**：Spring的JDK动态代理和CGlib动态代理



-----



##### Bean的生命周期

1) Bean工厂通过反射并创建Bean对象，进行**实例化**，此时Bean是个空对象
2) 对Bean进行**初始化**，对Bean对象的属性进行填充
2) 调用aware接口的方法，aware接口存在的意义是，方便通过Spring中Bean对象来获取对应容器中相关属性值
2) 执行before(前置增强)
2) 调用init方法
2) 执行after(后置增强)
2) 得出完整对象，可以直接使用
2) 对象的销毁过程



-----



### 容器和对象的创建流程

1) 先创建容器BeanFactory
2) 通过BeanDefintionReader加载配置文件，然后封装成BeanDefinition对象，存储在BeanFactory中
3) 调用执行BeanFactoryPostProcessor，
4) BeanFactory创建Bean之前，要做准备工作，例如准备BeanPostProcessor，监听器，事件，广播器
5) 实例化，创建空Bean
6) 初始化，填充Bean属性，BeforBeanPostProcessor，init，AfterBeanPostProcessor
7) 获取到完整Bean对象
8) Bean的销毁



-----



### Spring循环依赖问题

**循环依赖是怎么产生的**

```java
public class A {
    private B b;
    //省略构造方法和Set/Get方法
}

public class B{
    private A a;
    //省略构造方法和Set/Get方法
}
```

如上例，A对象中有私有属性：B对象，B中有私有属性：A对象，当Spring去创建A或B对象时，就会去创建另一个对象，另一个对象又因为却少当前对象，最后形成闭环操作，此现象称为：**循环依赖**

Java给属性赋值有两种方式：

构造器：无法解决循环依赖问题

Set方法：通过三级缓存来解决循环依赖问题



##### 依赖循环步骤：

1) 创建A对象
2) 实例化A对象，B = null
3) 填充A对象中的B属性
4) 从容器中查找B对象，如果有则直接赋值，如果没有则创建B对象
5) 此时容器中没有B对象，则创建B对象
6) 实例化B对象，A = null
7) 填充B对象中的A属性
8) 从容器中查找A对象，如果有则直接赋值，如果没有则创建A对象
9) 回到步骤1，形成闭环



-----



### 三级缓存

![](..\img\Spring\Spring三级缓存流程图.png)

##### **解决方案：**

在进行到步骤2的时候，将半成品的A放到一个Map集合(二级缓存)当中，只要有了当前对象的引用地址，后续也可以进行赋值，此时步骤4就可以从Map缓存中获取到半成品的A对象从而完成B对象的实例化，得到半成品B对象，将半成品B对象放到Map缓存中，此时半成品A对象就可以从容器中取得半成品B对象从而完成A对象的创建，得到一个完整的A对象，此时A对象中的B属性为半成品对象，所以需要将完整A对象存储到一个Map缓存中，但不能放到用来存储半成品的Map集合中，因为容易取到半成品的对象，所以要进行隔离，所以需要创建存储完整品对象的Map缓存集合(一级缓存)，此时已经有两个Map缓存，在创建A对象半成品但没放入缓存之前，会进行一个操作，会先判断一级缓存里有没有半成品A对象，如果没有，则会往三级缓存中存储一条信息，Key为半成品A对象，value为lambda表达式，然后将半成品A存入二级缓存中，经过判断二级缓存中没有B对象，所以进行B对象的创建，然后又创建出了B的半成品，又往三级缓存和二级缓存中插入一条信息，之后又进行B对象中A属性的创建，此次是第三次调用getBean方法，创建A对象时，经过一个判断，判断当前要创建的对象是否在创建中，第一次判断A是否在创建中，不在所以不进行任何操作，第二次判断B对象是否在创建中，不在创建中所以不进行任何操作，此次A正在创建中所以满足条件，所以从三级缓存中取出了lambda表达式的回调函数返回的A对象的代理对象，将A对象填充到B对象的属性中的到了一个完整的B对象，然后将三级缓存中的B对象所对应的信息删除，将完整的B对象存储到一级缓存中，然后A对象没有创建完成，但是能从一级缓存中取到完整的B对象，将B对象填充进A对象的属性中，得到一个完整的A对象，现在A对象和B对象都创建完成了，清除二级缓存和三级缓存，循环依赖问题解决完毕！

**一级缓存**：存储成品对象：singletonObjects

**二级缓存**：存储半成品对象：earlySingletorObjectsa

**三级缓存**：存储lambda表达式：singletonFactories(函数式接口)：可以传递一个lambda表达式进来，在进行调用的时候使用getObject来调用lambda表达式中的方法

##### 只有二级缓存也能解决循环依赖问题，那三级缓存到底做了什么？

三级缓存中存储了lambda表达式，该表达式调用了getEarlyBeanReference()方法，在这个方法中，实现了容器中代理对象和非代理对象的替换，如果只有二级缓存，现在容器中存储着一个非代理对象，如果现在需要代理对象，则无法获取，因为Spring容器中无法存储两个同名的代理和非代理对象，因为容器中对象都是单例的，意味着根据名称只能获取一个对象的值，如果此时存在两个对象的话，就无法判断使用的时候取哪一个对象，因为无法确认什么时候会调用当前对象，是在其他对象的执行过程中来进行调用的，而不是人为指定的，所以必须要保证容器中任何时候只有一个对象供外部调用，所以在三级缓存中完成了代理对象和非代理对象之间替换的工作，确认返回的是唯一的对象

**结论**

三级缓存是为了解决在AOP代理过程中产生的循环依赖问题，使用的是回调函数

如果没有AOP的话，二级缓存足以解决循环依赖的问题

-----

