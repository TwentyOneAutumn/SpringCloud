# RabbitMQ



### RabbitMQ依赖配置

1. 导入SpringAMQP依赖，其中包含RabbitMQ依赖

   ```xml
   <!-- SpringAMQP依赖 -->
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-amqp</artifactId>
   </dependency>
   ```

2. 在Publisher中配置RabbitMQ

   ```yaml
   spring:
     rabbitmq:
       host: 192.168.110.130 # IP
       port: 5672 # 端口
       virtual-host: / # 虚拟主机
       username: root
       password: root
   ```

3. 注入RabbitTemplate对象，并调用其方法发送消息到指定Queue

   ```java
   @RestController
   @RequestMapping("/order")
   public class OrderController {
   
       @Autowired
       private OrderServiceImpl orderServiceImpl;
   
       @Autowired
       UserClient userClient;
   
       @Autowired
       RabbitTemplate rabbitTemplate;
   
       @GetMapping("/{orderId}")
       public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
           // 根据id查询订单
           Order order = orderServiceImpl.getById(orderId);
           // order判空
           if (order == null) {
               return null;
           }
           // 调用Feign查询对应User信息并填充到Order对象
           order.setUser(userClient.queryById(order.getUserId()));
           
           // 调用rabbitTemplate发送消息
           rabbitTemplate.convertAndSend("Work.Queue", order.toString());
           
           return order;
       }
   }
   ```

4. 在Publisher中引入依赖并配置RabbitMQ

   ```yaml
   spring:
     rabbitmq:
       host: 192.168.110.130 # IP
       port: 5672 # 端口
       virtual-host: / # 虚拟主机
       username: root
       password: root
       listener:
         simple:
           prefetch: 1 # 每次从消息队列拿到消息的数量
   ```

5. Consumer绑定并监听指定队列，有消息发送到队列中时，会自动获取消息并进行处理

   ```java
   /**
    * 监听并接收Order发送的消息并做处理
    */
   @Component
   public class orderMqListener {
       /**
        * 监听队列 : Work.Queue
        *
        * @param order 队列消息
        * @throws InterruptedException 线程异常
        */
       @RabbitListener(queues = "Work.Queue")
       public void listenWorkQueuesMessage1(String order) throws InterruptedException {
           System.out.println("接收到来自Work.Queue队列的消息:[ " + order + " ]" + "," + " 接收时间:[ " + new Date().toString() + " ]");
       }
   }
   ```

   以上队列一条消息只能被一个消费者消费，但是可以定义多个方法来同时监听同一个队列，增强其处理速度

   例如Publisher每秒中发送20条消息，Consumer1和Consumer2每秒各处理15条，则不会引起消息堆积，此时消息处理效率等于Consumer1+Consumer2的每秒处理效率

   注意：消息一旦被消费，就会从队列删除，RabbitMQ没有消息回溯功能



为了解决一条消息不能被多个消费者消费，可以使用以下发布订阅模型

**Publisher	→	Exchange	→	Queue	→	Consumer**

在此模型下，消息会发送给 **Exchange(交换机)**，然后由交换机将消息路由到相应队列中

注意：Exchange负责消息的路由，而不是存储，路由失败则消息丢失



### Exchange



Fanout Exchange( 广播模式 ) ：会将接收到的消息路由到每一个跟其绑定的Queue

```Java
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    UserClient userClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单
        Order order = orderServiceImpl.getById(orderId);
        // order判空
        if (order == null) {
            return null;
        }
        // 调用Feign查询对应User信息并填充到Order对象
        order.setUser(userClient.queryById(order.getUserId()));
        
        // 调用rabbitTemplate发送消息
        rabbitTemplate.convertAndSend("FanoutExchange", null,order.toString());
        
        return order;
    }
}
```
```java
/**
 * 监听并接收Order发送的消息并做处理
 */
@Component
public class orderMqListener {
    /**
     * 监听队列 : FanoutQueue1
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Fanout.Queue1"),
                    exchange = @Exchange(name = "FanoutExchange",type = ExchangeTypes.FANOUT)
            )
    )
    public void listenFanoutQueues1Message(String order){
        System.out.println("FanoutQueue1Listen接受到消息:[ "+order + " ]" +" 时间为:[ " + new Date().toString() + " ]");
    }

    /**
     * 监听队列 : FanoutQueue2
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Fanout.Queue2"),
                    exchange = @Exchange(name = "FanoutExchange",type = ExchangeTypes.FANOUT)
            )
    )
    public void listenFanoutQueues2Message(String order){
        System.out.println("FanoutQueue2Listen接受到消息:[ "+order + " ]" +" 时间为:[ " + new Date().toString() + " ]");
    }
}
```



Direct Exchange( 路由模式 ) ：会将接收到的消息路由到指定的Queue，通过判断BingdingKey和RoutingKet是否一致来决定

```Java
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    UserClient userClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单
        Order order = orderServiceImpl.getById(orderId);
        // order判空
        if (order == null) {
            return null;
        }
        // 调用Feign查询对应User信息并填充到Order对象
        order.setUser(userClient.queryById(order.getUserId()));
        
        // 调用rabbitTemplate发送消息
        rabbitTemplate.convertAndSend("DirectExchange", "Queue1", order.toString());
        
        return order;
    }
}
```

```java
/**
 * 监听并接收Order发送的消息并做处理
 */
@Component
public class orderMqListener {
      /**
     * 监听队列 : Direct.Queue1
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Direct.Queue1"),
                    exchange = @Exchange(name = "DirectExchange",type = ExchangeTypes.DIRECT),
                    key = {"Queue1"}
            )
    )
    public void listenDirectQueues1MessageKeyIsDemo1AndDemo3(String order){
        System.out.println("DirectQueue1Listen接受到消息:[ "+order + " ]");
    }

    /**
     * 监听队列 : Direct.Queue2
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Direct.Queue2"),
                    exchange = @Exchange(name = "DirectExchange",type = ExchangeTypes.DIRECT),
                    key = {"Queue2"}
            )
    )
    public void listenDirectQueues2MessageKeyIsDemo2AndDemo3(String order){
        System.out.println("DirectQueue2Listen接受到消息:[ "+order + " ]");
    }
}
```



Topic Exchange( 发布订阅模式 ) ：会将接收到的消息路由到指定的Queue，通过判断BingdingKey和RoutingKet是否一致来决定

RoutingKey必须是多个单词组成，并以 **. **分割

BingdingKey可以使用通配符

#：代表0或多个单词

*：代表一个单词

```Java
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    UserClient userClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单
        Order order = orderServiceImpl.getById(orderId);
        // order判空
        if (order == null) {
            return null;
        }
        // 调用Feign查询对应User信息并填充到Order对象
        order.setUser(userClient.queryById(order.getUserId()));
        
        // 调用rabbitTemplate发送消息
        rabbitTemplate.convertAndSend("TopicExchange", "china.henan.meishi", order.toString());
        
        return order;
    }
}
```

```java
/**
 * 监听并接收Order发送的消息并做处理
 */
@Component
public class orderMqListener {
       /**
     * 监听队列 : Topic.Queue1
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue1"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"henan.*.meishi"}
            )
    )
    public void listenTopicQueues1Message(String order){
        System.out.println("TopicQueue1Listen接受到消息:[ "+order + " ]");
    }

    /**
     * 监听队列 : Topic.Queue2
     * @param order 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue2"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"#.meishi"}
            )
    )
    public void listenTopicQueues2Message(String order){
        System.out.println("TopicQueue2Listen接受到消息:[ "+order + " ]");
    }
}
```



Spring消息对象的处理是由MessageConverter来处理的，默认的实现是基于JDK的ObjectOutstream完成序列化操作的，由于该实现的性能以及对于数据的传输序列化效果不是很理想，所以需要配置声明JacksonMessageConverter去覆盖原本的消息处理器

1. 在Publisher和Consumer中分别引入Jackson依赖

   ```xml
   <!-- RabbitMQ消息转Json依赖 -->
   <dependency>
       <groupId>com.fasterxml.jackson.dataformat</groupId>
       <artifactId>jackson-dataformat-xml</artifactId>
       <version>2.13.2</version>
   </dependency>
   ```

2. 在Publisher和Consumer中分别声明Bean去覆盖Spring默认配置

   ```java
   /**
    * RabbitMq配置类
    */
   @Configuration
   public class RabbitMqConfiguration {
       /**
        * 用Jackson2JsonMessageConverter覆盖系统默认消息转换器
        * @return Jackson2JsonMessageConverter
        */
       @Bean
       public MessageConverter messageConverter(){
          return new Jackson2JsonMessageConverter();
       }
   }
   ```

这样进行消息发送和消息接收时，Jackson会自动帮我们实现数据的序列化和反序列化，就可以通过对象的方式去传输消息



---



### confirm和return模式

##### confirm

为了保证消息从消费端到交换机时的可靠性，会触发一个confirmCallabck的回调函数，通过函数中Ack参数来判断消息是否正确到达交换机，从而保证消息从生产端到交换机之间的消息可靠性

1. 开启RabbitMQ的confirm模式

   ```yaml
   spring:
     rabbitmq:
       publisher-confirm-type: correlated  # 将confirm设置为发布模式，默认为NONE禁用
   ```

2. 在RabbitTemplate对象中setConfirmCallback函数

   ```java
   public RabbitTemplate rabbitTemplate() {
       RabbitTemplate rabbitTemplate = new RabbitTemplate();
       // 设置confirm回调函数
       rabbitTemplate.setConfirmCallback(
           /*
                    *  correlationData : 消息的相关配置信息
                    *  ack : 是交换机否成功收到消息
                    *  cause : 失败原因
                    */
           (correlationData, ack, cause) -> {
   
           }
       );
       
       return rabbitTemplate;
   }
   ```



##### return

为了保证消息从消费端到交换机时消息的可靠性，可以设置RabbitMQ的消息路由失败后的处理模式，并触发一个returnCallabck的回调函数，从而保证消息从交换机到队列之间的消息可靠性

1. 开启RabbitMQ的return模式

   ```yaml
   spring:
     rabbitmq:
       publisher-returns: true # 开启回退模式
   ```

2. 在RabbitTemplate对象中setReturnCallback函数

   ```java
   public RabbitTemplate rabbitTemplate() {
       RabbitTemplate rabbitTemplate = new RabbitTemplate();
    
       // 设置交换机路由失败之后,消息的处理模式
       // 默认为false,即丢弃消息
       // true消息路由失败后,将消息返回给生产端
       rabbitTemplate.setMandatory(true);
   
       // 设置return回调函数
       rabbitTemplate.setReturnsCallback(
           returned -> {
               // 消息对象
               Message message = returned.getMessage();
               // 交换机
               String exchange = returned.getExchange();
               // 路由Key
               String routingKey = returned.getRoutingKey();
               // 错误信息
               String replyText = returned.getReplyText();
               // 错误码
               int replyCode = returned.getReplyCode();
           }
       );
       return rabbitTemplate;
   }
   ```



---



### Ack

为了保证消费端接收到消息时，消费信息的过程中出现异常，导致消息丢失，所以启用Ack手动签收模式，在执行完业务逻辑后，手动签收消息，如果出现异常则拒收消息，由Broker重新发送

1. 开启Ack手动签收

   ```yaml
   spring:
     rabbitmq:
       listener:
         simple:
           acknowledge-mode: manual  # Ack:开启回退模式手动确认
   ```

2. 处理业务时手动签收，如果出现异常，则拒收消息

   ```java
   @RabbitListener(queues = {orderConstants.DELETEONE_QUEUE})
   public void DirectExchangeListener1(Order order, Message message, Channel channel) throws IOException {
       try {
           // 执行业务逻辑
           orderClient.deleteOne(order);
           // 签收消息
           channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
       }
       catch (Exception e){
           /*
                * long deliveryTag:消息的唯一ID
                * boolean multiple: false表示仅拒绝当前消息，true表示拒绝当前消息之前所有未被当前消费者确认的消息
                * boolean requeue:被拒收的消息是否重新发送
                */
           channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);
       }
   }	
   ```

   



---



### 死信队列

##### 什么是死信

消息成为死信的三种情况

1. 消息被channel.basicNack()或 channel.basicReject()方法拒收
2. 消息在队列中的存活时间(TTL)到期
3. 消息队列 的消息数量已经超过最大队列长度

在满足以上三种条件任意一种时，消息就会称为死信

消息称为死信后，一般情况下会被直接丢弃，但为了保证业务的消息数据不丢失，就需要用到死信队列机制来处理死信



##### 如何配置死信队列

1. 配置业务队列和业务交换机并绑定
2. 配置死信交换机和死信队列并绑定
3. 为业务队列配置死信交换机和死信路由Key

并不是声明一个公共的死信队列，而是为每一个需要使用死信处理的业务队列单独配置一个死信交换机及队列



配置业务队列，死信队列，业务交换机，死信交换机

```java
package com.demo.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pojo.orderConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMq配置类
 * 用于声明交换机和队列并使其绑定
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 用Jackson2JsonMessageConverter覆盖系统默认消息转换器
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*
     * Order业务交换机
     */
    @Bean("DIRECTEXCHANGE")
    public DirectExchange DirectExchange(){
        return new DirectExchange(orderConstants.DIRECTEXCHANGE);
    }

    /*
     * 死信交换机
     */
    @Bean("DLQ_DIRECTEXCHANGE")
    public DirectExchange DLQDirectExchange(){
        return new DirectExchange(orderConstants.DLQ_DIRECTEXCHANGE);
    }

    /*
     * 删除单个Order所用Queue
     */
    @Bean("DELETEONE_QUEUE")
    public Queue orderDeleteOneQueue(){
        HashMap<String, Object> args = new HashMap<>();
        // 声明队列消息过期时间
        args.put("x-message-ttl",(java.lang.Long)(10000L));
        // 设置当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", orderConstants.DLQ_DIRECTEXCHANGE);
        // 设置当前队列绑定的死信交换机所对应的RoutingKey
        args.put("x-dead-letter-routing-key", orderConstants.DLQ_DELETEONE_KEY);
        return new Queue(orderConstants.DELETEONE_QUEUE,true,false,false,args);
    }

    /*
     * 绑定DeleteOne的队列和交换机
     */
    @Bean
    public Binding bindingDeleteOne(@Qualifier("DELETEONE_QUEUE")Queue DELETEONE_QUEUE,
                                    @Qualifier("DIRECTEXCHANGE") Exchange DIRECTEXCHANGE){
        return BindingBuilder.bind(DELETEONE_QUEUE).to(DIRECTEXCHANGE).with(orderConstants.DELETEONE_KEY).noargs();
    }

    /*
     * 绑定DeleteOne的队列和交换机
     */
    @Bean
    public Binding bindingDeleteMany(@Qualifier("DELETEMANY_QUEUE")Queue DELETEMANY_QUEUE,
                                     @Qualifier("DIRECTEXCHANGE")Exchange DIRECTEXCHANGE){
        return BindingBuilder.bind(DELETEMANY_QUEUE).to(DIRECTEXCHANGE).with(orderConstants.DELETEMANY_KEY).noargs();
    }

    /*
     * 绑定DeleteOne的队列和交换机
     */
    @Bean
    public Binding bindingInsertOne(@Qualifier("INSERTONE_QUEUE")Queue INSERTONE_QUEUE,
                                    @Qualifier("DIRECTEXCHANGE")Exchange DIRECTEXCHANGE){
        return BindingBuilder.bind(INSERTONE_QUEUE).to(DIRECTEXCHANGE).with(orderConstants.INSERTONE_KEY).noargs();
    }

    /*
     * 绑定DeleteOne的队列和交换机
     */
    @Bean
    public Binding bindingInsertMany(@Qualifier("INSERTMANY_QUEUE")Queue INSERTMANY_QUEUE,
                                     @Qualifier("DIRECTEXCHANGE")Exchange DIRECTEXCHANGE){
        return BindingBuilder.bind(INSERTMANY_QUEUE).to(DIRECTEXCHANGE).with(orderConstants.INSERTMANY_KEY).noargs();
    }

    /*
     * 删除单个Order所用死信队列
     */
    @Bean("DLQ_DELETEONE_QUEUE")
    public Queue DlqDeleteOneQueue(){
        return new Queue(orderConstants.DLQ_DELETEONE_QUEUE);
    }

    /*
     * 绑定死信DeleteOne的队列和交换机
     */
    @Bean
    public Binding bindingDlqDeleteOne(@Qualifier("DLQ_DELETEONE_QUEUE") Queue DLQ_DELETEONE_QUEUE,
                                       @Qualifier("DLQ_DIRECTEXCHANGE") Exchange DLQ_DIRECTEXCHANGE){
        return BindingBuilder.bind(DLQ_DELETEONE_QUEUE).to(DLQ_DIRECTEXCHANGE).with(orderConstants.DLQ_DELETEONE_KEY).noargs();
    }
}

```

1. 设置后发送消息到业务交换机
2. 业务交换机路由消息到业务队列
3. 业务队列在经过一定时间后，将所有消息都抛弃
4. 这些消息会被转发到该队列绑定的死信交换机中
5. 死信交换机会将消息路由给死信队列
6. 监听死信队列的消费端取出消息，并做业务处理



### 延迟队列

##### 为什么需要延迟队列？

有一个订单系统发送消息到延迟队列中，然后30分钟后，库存系统才能从延迟队列中获取该条消息，根据消息去数据库进行查询，判断订单状态，如果订单支付了则什么都不做，如果订单未支付则取消订单回滚库存



##### 什么是延迟队列？

RabbitMQ中并未提供延迟队列的功能，但是可以使用TTL+死信队列的组合实现延迟队列的效果

1. 订单系统发送消息到A交换机
2. A交换机将消息转发到A队列
3. A队列设置了消息过期时间为30分钟，且设置了并绑定了死信交换机B，并且没有其他消费端监听A队列
4. 30分钟后A队列的消息全部过期，并且消息转为死信，将会发送到死信交换机B中
5. 死信交换机B转发消息到死信队列B
6. 死信消费端监听死信队列B，并从中获取消息
7. 在死信消费端对消息进行判断并做相应处理



---



### RabbitMQ介绍

1. 实现AMQP协议，基于Erlang语言开发的一款消息中间件
2. 多种路由的模式
3. 支持多种语言
4. 支持AMQP，STOMP，MQTT等多种消息中间件协议



---



### AMQP

##### Module Layer

协议最高层，主要定义了一些客户端调用的命令，客户端可以用这些命令实现自己的业务逻辑。

##### Session Layer

中间层，主要负责客户端命令发送给服务器，再将服务端应答返回客户端，提供可靠性同步机制和错误处理。

##### TransportLayer

最底层，主要传输二进制数据流，提供帧的处理、信道服用、错误检测和数据表示等



---



### 消息从Producer到消费者Consumer的过程

1. Producer发送消息，设置消息体，BingdingKey等
2. 连接到Broker，由Broker建立连接，开启一个信道
3. 声明交换机并设置属性
4. 声明队列并设置属性
5. 将交换机和队列绑定
6. 消息由Broker发送到Exchange
7. Exchange根据消息的BingdingKey和队列的RoutingKey进行匹配，将消息路由到匹配的队列中，如果没有找到，则根据mandatory的配置决定丢弃消息还是将消息返回给Producer
8. 关闭信道和连接
9. Consumer监听队列
10. Consumer连接到Broker，建立连接，开启信道
11. Consumer向Broker请求消费队列中的消息
12. Broker回应并投递队列中的消息
13. Consumer接收到消息进行消费
14. Consumer确认收到消息
15. RabbitMQ从队列中删除相应己经被确认的消息
16. 关闭信道和连接



---



### 交换机类型

1. Fanout Exchange：广播模式，会将接收到的消息路由到每一个跟其绑定的Queue
2. Direct Exchange：路由模式，会将接收到的消息路由到指定的Queue，通过判断BingdingKey和RoutingKet是否一致来决定
3. Topic Exchange：布订阅模式，会将接收到的消息路由到指定的Queue，通过判断BingdingKey和RoutingKet是否一致来决定，可以使用通配符来匹配BingdingKey和RoutingKet



---



### 死信队列

通过设置消息的x-dead-letter-exchange属性来绑定对应的死信交换机

当消息称为死信后就会被重新发送到死信交换机，并路由到对应的队列，然后被对应的消费者消费

消息称为死信的三种情况

1. channel.basicNack()方法被拒收，且requeue属性为false
2. 消息设置的TTL过期
3. 队列中消息满了



---



### 队列结构

##### rabbit_amqqueue_process

负责协议相关的消息处理，即接收生产者发布的消息、向消费者交付消息、处理消息的确认(包括生产端的 confirm 和消费端的 ack) 等。

##### backing_queue

消息存储的具体形式和引擎，并向 rabbit amqqueue process提供相关的接口以供调用。



---



### 消息状态

1. alpha: 消息内容和消息索引都存储在内存中 

2. beta: 消息内容保存在磁盘中，消息索引保存在内存中

3. gamma: 消息内容保存在磁盘中，消息索引在磁盘和内存中都有 

4. delta: 消息内容和索引都在磁盘中 

   

---



### 应用场景

1. 应用耦合度高
2. 流量并发高
3. 性能问题



---



### 延迟队列

通过TTL+死信队列的模式来完成延迟队列



---



### 如何保证RabbitMQ的消息可靠性和幂等性



##### 从生产者到消费端

开启confirm模式



##### RabbitMQ自身

开启RabbitMQ消息持久化，但是会下降性能和吞吐量，根据实际情况来在消息可靠性和效率之间做抉择

利用HaProxy开启RabbitMQ镜像集群，



##### 消费端

关闭ACK自动签收模式，开启手动签收

如果消息是为了单纯写入数据库，可以根据主键查询数据是否存在来判断消息是否消费，也可以利用主键唯一的唯一性来保证不会插入重复的数据

如果业务场景需要，可以结合Redis缓存，给消息设置一个唯一的ID，每次消息消费之前判断消息是否存在，不存在则添加，消费结束后删除该条缓存，如果发生异常，拒收消息并删除缓存
