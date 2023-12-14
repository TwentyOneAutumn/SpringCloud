package com.rabbitmq.publisher.controller;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.rabbitmq.doMain.MessageInfo;
import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.RoutingKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到weatherFanout交换机
     * @param messageInfo 消息对象
     * @return AjaxResult
     */
    @PostMapping("/fanout")
    public AjaxResult fanoutWeather(@Valid @RequestBody MessageInfo messageInfo){
        rabbitTemplate.convertAndSend(ExchangeEnum.WEATHER_FANOUT_EXCHANGE, messageInfo);
        return Build.ajax(true);
    }

    /**
     * 发送消息到weatherDirect交换机
     * @param messageInfo 消息对象
     * @return AjaxResult
     */
    @PostMapping("/direct")
    public AjaxResult directWeather(@Valid @RequestBody MessageInfo messageInfo){
        rabbitTemplate.convertAndSend(ExchangeEnum.WEATHER_DIRECT_EXCHANGE, RoutingKey.WEATHER_KEY, messageInfo);
        return Build.ajax(true);
    }

    /**
     * 发送消息到weatherTopic交换机
     * @param messageInfo 消息对象
     * @return AjaxResult
     */
    @PostMapping("/topic")
    public AjaxResult topicWeather(@Valid @RequestBody MessageInfo messageInfo){
        rabbitTemplate.convertAndSend(ExchangeEnum.WEATHER_TOPIC_EXCHANGE, RoutingKey.WEATHER_KEY, messageInfo);
        return Build.ajax(true);
    }

    /**
     * 发送消息到weatherHeaders交换机
     * @param messageInfo 消息对象
     * @return AjaxResult
     */
    @PostMapping("/headers")
    public AjaxResult headersWeather(@Valid @RequestBody MessageInfo messageInfo){
        rabbitTemplate.convertAndSend(ExchangeEnum.WEATHER_HEADERS_EXCHANGE, RoutingKey.WEATHER_KEY, messageInfo, msg -> {
            Map<String, Object> headers = msg.getMessageProperties().getHeaders();
            headers.put("weatherHeader","context");
            return msg;
        });
        return Build.ajax(true);
    }
}
