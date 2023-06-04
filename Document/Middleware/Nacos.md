# Nacos

### 启动Nacos服务

```shell
D:\Nacos\nacos-server-1.4.3\nacos\bin
startup.cmd -m standalone
```



### Nacos依赖

```xml
<!-- Nacos依赖 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>2021.0.1.0</version>
</dependency>
```



### Nacos配置

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 # nacos服务地址，默认为:localhost:8848
        cluster-name: 集群名称 # 集群名称
        namespace: 命名空间ID # 配置命名空间
        ephemeral: false # 是否为临时实例
        
OrderDemo:
  ribbon:
    NFLoadBalancerRuleClassName: com.alibaba.cloud.nacos.ribbon.NacosRule # 优先本地集群并做随机负载均衡
    

```

 

### Nacos服务分级存储模型

**一级** ：一级是服务，例如UserDemo服务

**二级** ：二级是集群，例如UserCluster集群

**三级** ：三级是实例，例如UserCluster集群部署的UserDemo服务器



### Nacos服务权重配置

![Nacos权重配置1](D:\Typora\Java笔记\img\Nacos权重配置1.png)



![Nacos权重配置2](D:\Typora\Java笔记\img\Nacos权重配置2.png)



![Nacos权重配置3](D:\Typora\Java笔记\img\Nacos权重配置3.png)



### Nacos命名空间

![Nacos命名空间1](D:\Typora\Java笔记\img\Nacos命名空间1.png)



![Nacos命名空间2](D:\Typora\Java笔记\img\Nacos命名空间2.png)



![Nacos命名空间3](D:\Typora\Java笔记\img\Nacos命名空间3.png)



![Nacos命名空间4](D:\Typora\Java笔记\img\Nacos命名空间4.png)



> **注意 ：服务只能访问同一命名空间下的其他服务，对于其它命名空间的服务相互不可见，起到了环境隔离的作用**

