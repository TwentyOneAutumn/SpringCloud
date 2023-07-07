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
