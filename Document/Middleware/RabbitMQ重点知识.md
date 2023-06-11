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



### Publisher

生产者（Publisher）用于生产消息，将消息交给交换机（Exchange），由交换机通过规则路由到相应的队列中



---



### Exchange

交换机（Exchange）用于接收生产者发送的消息并根据规则将消息路由到一个或多个队列

1. 扇形交换机（Fanout Exchange）：它会将消息广播到所有绑定到该交换机的队列。无视消息的路由键，它只关注绑定到它的队列。
2. 直连交换机（Direct Exchange）：它根据消息的路由键（Routing Key）将消息路由到与之完全匹配的队列。只有当消息的路由键与队列绑定时指定的路由键完全匹配时，消息才会被投递到该队列。
3. 主题交换机（Topic Exchange）：它根据消息的路由键模式进行匹配，将消息路由到一个或多个与之匹配的队列。路由键可以是通配符形式的模式，例如 `*.key`、`#` 等，RoutingKey必须是多个单词组成，并以 `.` 分割，BingdingKey可以使用通配符，`#`：代表0或多个单词，`*`：代表一个单词
4. 头交换机（Headers Exchange）：它使用消息的头部信息进行匹配，并根据匹配结果将消息路由到与之匹配的队列。在使用头交换机时，需要指定一组键值对的规则来匹配消息的头部信息。



### 声明交换机

```java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置类
 */
@Configuration
public class ExchangeConfig {

    /**
     * 天气广播交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_FANOUT_EXCHANGE)
    public FanoutExchange weatherFanoutExchange() {
        return new FanoutExchange(ExchangeEnum.WEATHER_FANOUT_EXCHANGE,true,true);
    }

    /**
     * 天气直连交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DIRECT_EXCHANGE)
    public DirectExchange weatherDirectExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DIRECT_EXCHANGE,true,true);
    }

    /**
     * 天气主题交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)
    public TopicExchange weatherTopicExchange() {
        return new TopicExchange(ExchangeEnum.WEATHER_TOPIC_EXCHANGE,true,true);
    }


    /**
     * 天气头部交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_HEADERS_EXCHANGE)
    public HeadersExchange weatherHeadersExchange() {
        return new HeadersExchange(ExchangeEnum.WEATHER_HEADERS_EXCHANGE,true,true);
    }
}

```



---



### Queue

在消息队列系统中，一个队列（Queue）是用于存储消息的数据结构。它是消息的缓冲区，用于在生产者和消费者之间传递数据

1. **FIFO（First-In-First-Out）顺序：** 队列通常遵循先进先出的原则，即最早进入队列的消息将首先被消费者接收和处理。
2. **点对点通信模式：** 在点对点（Point-to-Point）通信模式中，一个消息只能被一个消费者接收和处理。消费者通过订阅特定的队列来接收消息。
3. **发布-订阅通信模式：** 在发布-订阅（Publish-Subscribe）通信模式中，消息被广播到所有订阅了相关主题（Topic）的消费者。消费者通过订阅特定的主题来接收消息。
4. **持久化：** 队列可以配置为持久化消息，确保即使在重启或系统故障后，存储在队列中的消息也不会丢失。
5. **自动删除：** 队列可以配置为在消费者断开连接后自动删除。如果所有消费者都断开连接，队列中的消息也将被删除。
6. **消息处理机制：** 队列可以使用不同的消息处理机制，例如确认机制、消息优先级、消息超时等。
7. **队列管理：** 队列可以由队列管理器进行管理，包括创建、删除、监控队列的状态、限制队列的大小等。



### 声明队列

```java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.QueueEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置类
 */
@Configuration
public class QueueConfig {

    /**
     * 天气广播队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_FANOUT_QUEUE)
    public Queue weatherFanoutQueue() {
        return new Queue(QueueEnum.WEATHER_FANOUT_QUEUE,true,false,false);
    }

    /**
     * 天气直连队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DIRECT_QUEUE)
    public Queue weatherDirectQueue() {
        return new Queue(QueueEnum.WEATHER_DIRECT_QUEUE,true,false,false);
    }

    /**
     * 天气主题队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_TOPIC_QUEUE)
    public Queue weatherTopicQueue() {
        return new Queue(QueueEnum.WEATHER_TOPIC_QUEUE,true,false,false);
    }

    /**
     * 天气头队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_HEADERS_QUEUE)
    public Queue weatherHeadersQueue() {
        return new Queue(QueueEnum.WEATHER_HEADERS_QUEUE,true,false,false);
    }
}

```



---



### Binding Exchange And Queue

```java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置类
 */
@Configuration
public class ExchangeConfig {

    /**
     * 天气广播交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_FANOUT_EXCHANGE)
    public FanoutExchange weatherFanoutExchange() {
        return new FanoutExchange(ExchangeEnum.WEATHER_FANOUT_EXCHANGE,true,true);
    }

    /**
     * 天气直连交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DIRECT_EXCHANGE)
    public DirectExchange weatherDirectExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DIRECT_EXCHANGE,true,true);
    }

    /**
     * 天气主题交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)
    public TopicExchange weatherTopicExchange() {
        return new TopicExchange(ExchangeEnum.WEATHER_TOPIC_EXCHANGE,true,true);
    }


    /**
     * 天气头部交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_HEADERS_EXCHANGE)
    public HeadersExchange weatherHeadersExchange() {
        return new HeadersExchange(ExchangeEnum.WEATHER_HEADERS_EXCHANGE,true,true);
    }
}

```



---



### Consumer

在消息队列系统中，消费者（Consumer）是消息的接收者和处理者。它们是消息队列中的订阅者，负责从队列中获取消息并执行相应的业务逻辑

1. **订阅队列：** 消费者通过订阅特定的队列或主题（Topic）来接收消息。它们可以选择订阅一个或多个队列，或者按照特定的主题进行订阅。
2. **消息拉取和推送：** 消费者可以使用消息拉取模式或消息推送模式来接收消息。在消息拉取模式中，消费者主动从队列中拉取消息，而在消息推送模式中，消息队列系统将消息推送给消费者。
3. **并发处理：** 消费者可以以并发的方式处理消息，通过多个消费者实例来提高消息的处理速度和吞吐量。消息队列系统通常支持在多个消费者之间进行消息的负载均衡和分发。
4. **消息确认：** 消费者在接收和处理消息后，通常需要向消息队列系统发送确认消息，表示该消息已经被成功处理。这种确认机制可以确保消息不会在处理过程中丢失。
5. **消息过滤：** 消费者可以使用过滤机制来选择性地接收特定类型的消息。这可以通过设置特定的消息属性、头部信息或使用特定的消息选择器来实现。
6. **错误处理和重试：** 消费者可能会遇到处理消息时的错误或异常情况。在这种情况下，消费者可以实施错误处理策略，例如重试处理、错误日志记录、消息死信队列等。
7. **消费者组：** 多个消费者可以组成一个消费者组，共同消费同一个队列或主题。在这种情况下，消息将被均衡地分发给消费者组中的消费者实例。



### 定义监听队列

```java
package com.rabbitmq.consumer.listener;

import com.rabbitmq.doMain.Weather;
import com.rabbitmq.enums.QueueEnum;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class WeatherListener {

    /**
     * 监听 天气广播队列消息
     * @param weather 数据对象
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = QueueEnum.WEATHER_FANOUT_QUEUE)
    public void listenFanoutQueuesMessage(Weather weather){
        System.out.println("接受到天气广播队列消息:[ "+ weather + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气直连队列消息
     * @param weather 数据对象
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = QueueEnum.WEATHER_DIRECT_QUEUE)
    public void listenDirectQueuesMessage(Weather weather){
        System.out.println("接受到天气直连队列消息:[ "+ weather + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气主题队列消息
     * @param weather 数据对象
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = QueueEnum.WEATHER_TOPIC_QUEUE)
    public void listenTopicQueuesMessage(Weather weather) {
        System.out.println("接受到天气主题队列消息:[ "+ weather + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气头部队列消息
     * @param weather 数据对象
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = QueueEnum.WEATHER_HEADERS_QUEUE)
    public void listenHeadersQueuesMessage(Weather weather) {
        System.out.println("接受到天气头部队列消息:[ "+ weather + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }
}
```



---



### confirm模式

- `confirm`模式用于确保消息的可靠传递，即生产者发送消息后，会收到确认信息，以确保消息已经成功到达RabbitMQ服务器。
- 当消息成功发送到RabbitMQ服务器后，RabbitMQ会发送一个确认回执给生产者。如果消息发送失败，RabbitMQ会发送一个拒绝回执给生产者。
- 生产者可以通过设置来启用`confirm`模式，然后定义`回调函数`，以处理确认和拒绝回执。
- 在确认回调函数中，可以根据`回执的状态（ACK）`来判断消息是否成功发送，并进行相应的处理，例如重新发送失败的消息或记录日志等。
- `confirm`模式适用于对消息的可靠性传递要求较高的场景，可以确保消息不会丢失

1. 开启RabbitMQ的confirm模式

   ```yaml
   spring:
     rabbitmq:
       publisher-confirm-type: correlated  # 将confirm设置为发布模式，默认为NONE禁用
   ```

   **none**：表示不启用发布者确认模式。在这种模式下，生产者发送消息后不会收到任何确认回执，也无法知道消息是否成功发送到RabbitMQ服务器。

   **correlated**：表示启用关联发布者确认模式。在这种模式下，生产者发送消息后，会收到一个与消息关联的确认回执。可以使用消息的唯一标识符来关联确认回执和消息。

   **simple**：表示启用简单发布者确认模式。在这种模式下，生产者发送消息后，会收到一个全局的确认回执。生产者无法通过回执与具体的消息进行关联，只能知道是否存在发送失败的情况。

2. 创建类实现ConfirmCallback接口，并重写confirm方法

   ```java
   package com.rabbitmq.config;
   
   import cn.hutool.core.bean.BeanUtil;
   import cn.hutool.json.JSONObject;
   import com.rabbitmq.doMain.ErrorMessage;
   import com.rabbitmq.service.IErrorMessageService;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.amqp.core.ReturnedMessage;
   import org.springframework.amqp.rabbit.connection.CorrelationData;
   import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.data.redis.core.RedisTemplate;
   import org.springframework.stereotype.Component;
   import java.time.LocalDate;
   import java.time.LocalDateTime;
   import java.time.format.DateTimeFormatter;
   import java.util.concurrent.TimeUnit;
   
   /**
    * 自定义Confirm处理类
    */
   @Slf4j
   @Component
   public class CustomConfirmCallback implements ConfirmCallback {
   
       @Autowired
       private IErrorMessageService errorMessageService;
   
       @Autowired
       private RedisTemplate<String,Object> redisTemplate;
   
       /**
        * 处理消息回执
        * @param correlationData 用于关联消息和确认回调。在发送消息时，您可以为每条消息设置一个唯一的CorrelationData对象，以便在确认回调中将确认与消息进行关联
        * @param ack 表示消息是否被确认，当ack为true时，表示消息已经成功到达RabbitMQ服务器；当ack为false时，表示消息发送失败
        * @param cause 字符串，表示发送失败的原因，当ack为false时，cause参数会包含发送失败的具体原因描述
        */
       @Override
       public void confirm(CorrelationData correlationData, boolean ack, String cause) {
           // 进行异常捕获
           try {
               ReturnedMessage returned = correlationData.getReturned();
               com.rabbitmq.doMain.MessageInfo messageInfo = BeanUtil.toBean(correlationData.getReturned().getMessage().getBody(), com.rabbitmq.doMain.MessageInfo.class);
               if(ack){
                   String userId = messageInfo.getUserId();
                   // 获取当前时间的 LocalDate
                   LocalDate currentDate = LocalDate.now();
                   // 定义日期格式化器
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                   // 格式化 LocalDate
                   String formattedDate = currentDate.format(formatter);
                   // 拼接Key
                   String key = userId + "#" + formattedDate;
                   // 将详细存储到Redis
                   if(redisTemplate.hasKey(key)){
                       redisTemplate.opsForValue().set(key,messageInfo.getMessage());
                   }else {
                       redisTemplate.opsForValue().set(key,messageInfo.getMessage(),7, TimeUnit.DAYS);
                   }
               }else {
                   log.info("消息发送失败,错误信息:[" + cause + "],交换机:[" + returned.getExchange() + "],路由键:[" + returned.getRoutingKey() + "]");
                   ErrorMessage errorMessage = new ErrorMessage(
                           correlationData.getId(),
                           returned.getReplyCode(),
                           cause,
                           LocalDateTime.now(),
                           returned.getExchange(),
                           returned.getRoutingKey(),
                           new JSONObject(messageInfo.getMessage()).toString()
                   );
                   // 将详细存储到数据库
                   boolean save = errorMessageService.save(errorMessage);
                   if(!save){
                       throw new RuntimeException("保存消息失败");
                   }
               }
           }catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
   
   ```

3. 在RabbitTemplate对象中setReturnCallback函数

   ```java
   package com.rabbitmq.config;
   
   import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
   import org.springframework.amqp.rabbit.core.RabbitTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   /**
    * RabbitMq配置类
    */
   @Configuration
   public class RabbitMqConfig {
   
       @Autowired
       private CustomConfirmCallback customConfirmCallback;
   
   
       /**
        * RabbitTemplate
        * @param connectionFactory RabbitMQ连接工厂
        * @return RabbitTemplate
        */
       @Bean
       public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
           RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
           rabbitTemplate.setConfirmCallback(customConfirmCallback);
           return rabbitTemplate;
       }
   }
   ```

   

---



### return模式

Return模式是RabbitMQ提供的一种消息机制，用于处理发送到交换机但无法被正确路由到任何队列的消息。当消息无法被正确路由时，RabbitMQ会将该消息返回给生产者，并触发Return Callback回调函数

1. 开启RabbitMQ的confirm模式

   ```yaml
   spring:
     rabbitmq:
       publisher-returns: correlated  # 开启return模式
   ```

2. 创建类实现RabbitTemplate.ReturnsCallback接口，并重写returnedMessage方法

   ```java
   package com.rabbitmq.config;
   
   
   import cn.hutool.core.bean.BeanUtil;
   import cn.hutool.json.JSONObject;
   import com.rabbitmq.doMain.ErrorMessage;
   import com.rabbitmq.doMain.MessageInfo;
   import com.rabbitmq.service.IErrorMessageService;
   import org.springframework.amqp.core.Message;
   import org.springframework.amqp.core.ReturnedMessage;
   import org.springframework.amqp.rabbit.connection.CorrelationData;
   import org.springframework.amqp.rabbit.core.RabbitTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.data.redis.core.RedisTemplate;
   import org.springframework.stereotype.Component;
   
   import java.time.LocalDateTime;
   import java.util.concurrent.TimeUnit;
   
   /**
    * 自定义返回回调
    */
   @Component
   public class CustomReturnCallback implements RabbitTemplate.ReturnsCallback {
   
       @Autowired
       private RabbitTemplate rabbitTemplate;
   
       @Autowired
       private RedisTemplate<String,Object> redisTemplate;
   
       @Autowired
       private IErrorMessageService errorMessageService;
   
       /**
        * 用于处理发送到交换机但无法被正确路由到任何队列的消息
        * @param returned 消息对象
        */
       @Override
       public void returnedMessage(ReturnedMessage returned) {
           Message message = returned.getMessage();
           String correlationId = message.getMessageProperties().getCorrelationId();
           // 重新发送消息，并保持相同的Correlation ID
           CorrelationData correlationData = new CorrelationData(correlationId);
           // 重新获取对象
           MessageInfo messageInfo = BeanUtil.toBean(message.getBody(), MessageInfo.class);
           String userId = messageInfo.getUserId();
           // 将重试次数存入Redis
           String key = userId + correlationId;
           int count = 1;
           if(redisTemplate.hasKey(key)){
               if(count < 5){
                   count = (int)redisTemplate.opsForValue().get(key);
                   redisTemplate.opsForValue().set(key,count);
                   // 重新发送消息
                   rabbitTemplate.convertAndSend(returned.getExchange(),returned.getRoutingKey(),messageInfo,correlationData);
               }else {
                   // 将消息存储到错误信息表中
                   ErrorMessage errorMessage = new ErrorMessage(
                           correlationId,
                           returned.getReplyCode(),
                           returned.getReplyText(),
                           LocalDateTime.now(),
                           returned.getExchange(),
                           returned.getRoutingKey(),
                           new JSONObject(messageInfo.getMessage()).toString()
                   );
                   // 将详细存储到数据库
                   boolean save = errorMessageService.save(errorMessage);
                   if(!save){
                       throw new RuntimeException("保存消息失败");
                   }
               }
           }else {
               // 重新发送消息并设置过期时间
               redisTemplate.opsForValue().set(key,count,7, TimeUnit.DAYS);
           }
       }
   }
   
   ```

3. 在RabbitTemplate对象中setReturnsCallback函数

   ```java
   package com.rabbitmq.config;
   
   import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
   import org.springframework.amqp.rabbit.core.RabbitTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   /**
    * RabbitMq配置类
    */
   @Configuration
   public class RabbitMqConfig {
   
       @Autowired
       private CustomReturnCallback customReturnCallback;
   
   
       /**
        * RabbitTemplate
        * @param connectionFactory RabbitMQ连接工厂
        * @return RabbitTemplate
        */
       @Bean
       public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
           RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
           rabbitTemplate.setReturnsCallback(customReturnCallback);
           return rabbitTemplate;
       }
   }
   ```

   

---



### 死信队列

死信队列（Dead Letter Queue）是RabbitMQ中的一种特殊队列，用于接收无法被消费或处理的消息。当消息满足一定条件时，例如消息过期、消息被拒绝、消息被消费者处理失败等情况，它们会被发送到死信队列中。

死信队列的作用主要有以下几个方面：

1. 错误处理和补救机制：当消息无法被正确处理时，可以将其发送到死信队列中，以便进行错误处理和补救。例如，对于处理失败的消息，可以将其保存到死信队列中，后续可以对这些消息进行分析、重试、排查问题等操作。
2. 延迟消息处理：通过结合延迟队列和死信队列，可以实现延迟消息的处理。当需要延迟发送消息时，可以将消息发送到延迟队列，设置消息的过期时间，当消息过期后会被发送到死信队列，从而触发消息的消费和处理。
3. 转发和路由失败消息：当消息无法被正确路由到任何队列时，可以将其发送到死信队列中。这种情况可能发生在消息的目标队列不存在或路由键无效等情况下。通过死信队列，可以对这些无法路由的消息进行后续处理，例如记录日志、报警或人工处理等。
4. 实现消息优先级：死信队列可以与优先级队列结合使用，实现消息的优先级处理。当消息满足一定条件被发送到死信队列时，可以将其重新发送到优先级队列中，并按照优先级顺序重新进行处理，确保高优先级消息能够被尽快处理。

总之，死信队列在消息队列系统中起着重要的作用。它提供了错误处理、延迟消息处理、无法路由消息处理和优先级消息处理等功能。通过合理配置和使用死信队列，可以提高消息系统的可靠性、灵活性和可维护性



### 交换机

```java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置类
 */
@Configuration
public class ExchangeConfig {

    /**
     * 天气延迟交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DELAY_EXCHANGE)
    public DirectExchange weatherDelayExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DELAY_EXCHANGE,true,true);
    }


    /**
     * 天气死信交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE)
    public DirectExchange weatherDeadLetterExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE,true,true);
    }
}
```



### 队列

````java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.DelayEnum;
import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.QueueEnum;
import com.rabbitmq.enums.RoutingKey;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 队列配置类
 */
@Configuration
public class QueueConfig {
    
    /**
     * 天气延迟队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DELAY_QUEUE)
    public Queue weatherDelayQueue() {
        HashMap<String, Object> args = new HashMap<>();
        // 声明队列消息过期时间
        args.put(DelayEnum.TTL,300L);
        // 设置当前队列绑定的死信交换机
        args.put(DelayEnum.DEAD_LETTER_EXCHANGE, ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE);
        // 设置当前队列绑定的死信交换机所对应的RoutingKey
        args.put(DelayEnum.DEAD_LETTER_ROUTING_KEY, RoutingKey.DEAD_LETTER_KEY);
        return new Queue(QueueEnum.WEATHER_DELAY_QUEUE,true,false,false,args);
    }


    /**
     * 天气死信队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DEAD_LETTER_QUEUE)
    public Queue weatherDeadLetterQueue() {
        return new Queue(QueueEnum.WEATHER_DEAD_LETTER_QUEUE,true,false,false);
    }
}
````



### 绑定

```java
package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.QueueEnum;
import com.rabbitmq.enums.RoutingKey;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 绑定 交换机 and 队列 配置类
 */
@Configuration
public class BindingConfig {
    
    @Bean
    public Binding weatherDelayBinding(@Qualifier(ExchangeEnum.WEATHER_DELAY_EXCHANGE)DirectExchange exchange, @Qualifier(QueueEnum.WEATHER_DELAY_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.WEATHER_KEY);
    }

    @Bean
    public Binding weatherDeadLetterBinding(@Qualifier(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE)DirectExchange exchange, @Qualifier(QueueEnum.WEATHER_DEAD_LETTER_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.DEAD_LETTER_KEY);
    }
}
```



---



### 手动ACK

