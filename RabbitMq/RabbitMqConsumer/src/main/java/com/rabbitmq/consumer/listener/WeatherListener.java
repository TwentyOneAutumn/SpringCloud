package com.rabbitmq.consumer.listener;

import com.rabbitmq.doMain.MessageInfo;
import com.rabbitmq.enums.QueueEnum;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import com.rabbitmq.client.Channel;

@Component
public class WeatherListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 监听 天气广播队列消息
     * @param messageInfo 数据对象
     */
    @RabbitListener(queues = QueueEnum.WEATHER_FANOUT_QUEUE)
    public void listenFanoutQueuesMessage(MessageInfo messageInfo){
        System.out.println("接受到天气广播队列消息:[ "+ messageInfo.getMessage() + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气直连队列消息
     * @param messageInfo 数据对象
     */
    @RabbitListener(queues = QueueEnum.WEATHER_DIRECT_QUEUE)
    public void listenDirectQueuesMessage(MessageInfo messageInfo){
        System.out.println("接受到天气直连队列消息:[ "+ messageInfo.getMessage() + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气主题队列消息
     * @param messageInfo 数据对象
     */
    @RabbitListener(queues = QueueEnum.WEATHER_TOPIC_QUEUE)
    public void listenTopicQueuesMessage(MessageInfo messageInfo) {
        System.out.println("接受到天气主题队列消息:[ "+ messageInfo.getMessage() + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气头部队列消息
     * @param messageInfo 数据对象
     */
    @RabbitListener(queues = QueueEnum.WEATHER_HEADERS_QUEUE)
    public void listenHeadersQueuesMessage(MessageInfo messageInfo) {
        System.out.println("接受到天气头部队列消息:[ "+ messageInfo.getMessage() + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }


    /**
     * 监听 天气手动ACK队列消息
     * @param messageInfo 数据对象
     */
    @RabbitListener(queues = QueueEnum.WEATHER_FANOUT_QUEUE, ackMode = "MANUAL")
    public void listenManualAckQueuesMessage(MessageInfo messageInfo, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {

            // 处理消息

            // 手动签收消息
            channel.basicAck(deliveryTag, false);
            System.out.println("接受到天气广播队列消息:[ "+ messageInfo.getMessage() + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
        }
        catch (Exception e) {
            // 记录日志等操作

            // 拒收消息
            channel.basicNack(deliveryTag,false,false);
        }
    }
}