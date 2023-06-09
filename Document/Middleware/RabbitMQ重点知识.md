# RabbitMQ



### RabbitMQ依赖

```xml
<!-- SpringAMQP依赖 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



---



### RabbitMQ配置

```yaml
spring:
  rabbitmq:
    host: 192.168.110.130 # IP
    port: 5672 # 端口
    virtual-host: / # 虚拟主机
    username: root
    password: 2762581@com
```



---



### Exchange

交换机（Exchange）用于接收生产者发送的消息并根据规则将消息路由到一个或多个队列



直连交换机（Direct Exchange）：它根据消息的路由键（Routing Key）将消息路由到与之完全匹配的队列。只有当消息的路由键与队列绑定时指定的路由键完全匹配时，消息才会被投递到该队列。

1. 主题交换机（Topic Exchange）：它根据消息的路由键模式进行匹配，将消息路由到一个或多个与之匹配的队列。路由键可以是通配符形式的模式，例如 `*.key`、`#` 等。
2. 头交换机（Headers Exchange）：它使用消息的头部信息进行匹配，并根据匹配结果将消息路由到与之匹配的队列。在使用头交换机时，需要指定一组键值对的规则来匹配消息的头部信息。
3. 扇形交换机（Fanout Exchange）：它会将消息广播到所有绑定到该交换机的队列。无视消息的路由键，它只关注绑定到它的队列。
4. 延迟交换机（Delay Exchange）：这是一种自定义的交换机类型，它用于实现延迟消息的投递。消息首先被发送到延迟交换机，然后根据指定的延迟时间将消息投递到对应的目标交换机和队列。

