package com.rabbitmq.consumer.listener;

import com.rabbitmq.doMain.Weather;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class WeatherListener {



    /**
     * 监听队列 :
     * @param msg 消息
     * @throws InterruptedException 线程异常
     */
    @RabbitListener(queues = "weatherQueue")
    public void listenWorkQueuesMessage1(Weather weather) throws InterruptedException {
        System.out.println("WeatherListen接受到消息:[ "+ weather + " ]" +" 时间为:[ " + LocalTime.now() + " ]");
    }
}
