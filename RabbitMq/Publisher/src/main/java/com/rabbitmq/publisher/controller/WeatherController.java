package com.rabbitmq.publisher.controller;

import com.core.doMain.AjaxResult;
import com.rabbitmq.doMain.Weather;
import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.RoutingKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/send")
    public AjaxResult sendWeather(Weather weather){
        rabbitTemplate.convertAndSend(ExchangeEnum.WEATHER_TOPIC_EXCHANGE, RoutingKey.WEATHER_KEY,weather);
        return AjaxResult.success();
    }
}
