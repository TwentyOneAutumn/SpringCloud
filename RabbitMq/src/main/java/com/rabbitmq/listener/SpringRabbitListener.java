package com.rabbitmq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class SpringRabbitListener{

    /**
     * 监听队列 : Work.Queue
     * @param msg 消息
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = "Work.Queue")
    public void listenWorkQueuesMessage1(String msg) throws InterruptedException {
        System.out.println("WorkQueueListen1接受到消息:[ "+msg + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
        Thread.sleep(20);
    }

    /**
     * 监听队列 : Work.Queue
     * @param msg 消息
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = "Work.Queue")
    public void listenWorkQueuesMessage2(String msg) throws InterruptedException {
        System.out.println("WorkQueueListen2接受到消息:[ "+msg + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
        Thread.sleep(200);
    }

    /**
     * 监听队列 : FanoutQueue1
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Fanout.Queue1"),
                    exchange = @Exchange(name = "FanoutExchange",type = ExchangeTypes.FANOUT)
            )
    )
    public void listenFanoutQueues1Message(String msg){
        System.out.println("FanoutQueue1Listen接受到消息:[ "+msg + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }

    /**
     * 监听队列 : FanoutQueue2
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Fanout.Queue2"),
                    exchange = @Exchange(name = "FanoutExchange",type = ExchangeTypes.FANOUT)
            )
    )
    public void listenFanoutQueues2Message(String msg){
        System.out.println("FanoutQueue2Listen接受到消息:[ "+msg + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }

    /**
     * 监听队列 : DirectQueue1
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Direct.Queue1"),
                    exchange = @Exchange(name = "DirectExchange",type = ExchangeTypes.DIRECT),
                    key = {"Demo1","Demo3"}
            )
    )
    public void listenDirectQueues1MessageKeyIsDemo1AndDemo3(String msg){
        System.out.println("DirectQueue1Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : DirectQueue2
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Direct.Queue2"),
                    exchange = @Exchange(name = "DirectExchange",type = ExchangeTypes.DIRECT),
                    key = {"Demo2","Demo3"}
            )
    )
    public void listenDirectQueues2MessageKeyIsDemo2AndDemo3(String msg){
        System.out.println("DirectQueue2Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : DirectQueue3
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Direct.Queue3"),
                    exchange = @Exchange(name = "DirectExchange",type = ExchangeTypes.DIRECT),
                    key = {"Demo3"}
            )
    )
    public void listenDirectQueues3MessageKeyIsDemo3(String msg){
        System.out.println("DirectQueue3Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : TopicQueue1
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue1"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"#.cat"}
            )
    )
    public void listenTopicQueues1Message(String msg){
        System.out.println("TopicQueue1Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : TopicQueue2
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue2"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"#.dog"}
            )
    )
    public void listenTopicQueues2Message(String msg){
        System.out.println("TopicQueue2Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : TopicQueue3
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue3"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"zhangsan.*"}
            )
    )
    public void listenTopicQueues3Message(String msg){
        System.out.println("TopicQueue3Listen接受到消息:[ "+msg + " ]");
    }

    /**
     * 监听队列 : TopicQueue4
     * @param msg 消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "Topic.Queue4"),
                    exchange = @Exchange(name = "TopicExchange",type = ExchangeTypes.TOPIC),
                    key = {"lisi.*"}
            )
    )
    public void listenTopicQueues4Message(String msg){
        System.out.println("TopicQueue4Listen接受到消息:[ "+msg + " ]");
    }


}
