# SpringBoot重点知识



### 注解

| 注解名                       | 说明                                                         |
| ---------------------------- | ------------------------------------------------------------ |
| @SpringBootApplication       | 标识当前类是SpringBoot项目的启动类，作用相当于@ComponentScan + @EnableAutoConfiguration + @Configuration，开启SpringBoot自动装配，扫描当前类所在包下及其子包，配置类 |
| @EnableAutoConfiguration     | 根据项目的依赖和配置，自动地配置应用程序的 Spring 配置       |
| @Conditional                 | 条件装配，用不同的子类实现可以完成不同条件下组件的注入       |
| @Import                      | 添加在Spring能扫描到的类的上面，通过传入字节码，默认调用Bean的无参构造器，向容器中生命一个Bean，Value为类名.class |
| @ImportResoure               | 加载配置文件，通过classpath属性指定配置文件的名称            |
| @EnableTransactionManagement | 开启Spring事务                                               |
| @Value                       | 获取值，通过@Value("${server.port}")来获取application.yml中的配置属性 |
| @ConditionalOnProperty       | 根据应用程序配置文件中的属性值，决定是否要启用某个配置类或组件 |
| @Import                      | 用于引入其他配置类，将其纳入当前配置类中                     |
| @DependsOn                   | 用于定义bean之间的依赖关系,确保某个 bean 在另一个bean之前初始化 |
| @ConditionalOnClass          | 用于根据类路径中是否存在某个特定的类来决定是否加载配置       |
| @ConditionalOnMissingClass   | 用于在类路径中不存在指定的类时加载配置                       |
| @ConditionalOnResource       | 用于根据类路径中是否存在指定资源文件来决定是否启用配置类中的一部分配置 |



### 自动装配原理

SpringBoot有自动装配功能，可以自动装配一些默认配置，减少繁琐的配置操作

SpringBoot通过@SpringBootApplication注解下的@EnableAutoConfiguration注解来开启SpringBoot的自动装配功能

自动装配功能会读取Maven中每个starter中的spring.factories文件，该文件配置了所有需要被Spring容器创建的Bean

然后对exclude和excludeName属性携带的类排除

然后经过@Conditional注解的子类注解，根据定义的规则筛选

把筛选完毕的Bean注入到Spring容器里去



---



### 事务

1. 需要在配置类上添加@EnableTransactionManagement注解开启SpringBoot事务
2. 在需要事务控制的方法上添加@Transactional注解便可



### 核心配置文件

bootstart和application

bootstart先加载，application后加载