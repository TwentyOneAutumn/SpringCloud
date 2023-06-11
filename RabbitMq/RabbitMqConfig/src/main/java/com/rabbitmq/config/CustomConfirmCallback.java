package com.rabbitmq.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.rabbitmq.doMain.ErrorMessage;
import com.rabbitmq.doMain.MessageInfo;
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
            MessageInfo messageInfo = BeanUtil.toBean(correlationData.getReturned().getMessage().getBody(), MessageInfo.class);
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
                // 判断Key是否存在
                Boolean hasKey = redisTemplate.hasKey(key);
                // 将详细存储到Redis
                redisTemplate.opsForHash().put(key, correlationData.getId(), messageInfo.getMessage());
                if(!hasKey){
                    // 如果Key不存在则设置过期时间为7天
                    redisTemplate.expire(key, 7, TimeUnit.DAYS);
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
