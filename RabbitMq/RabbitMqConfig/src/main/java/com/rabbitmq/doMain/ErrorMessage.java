package com.rabbitmq.doMain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 错误消息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("error_message")
public class ErrorMessage implements Serializable {

    /**
     * 主键ID
     */
    @TableId
    private String correlationId;

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误时间
     */
    private LocalDateTime errorTime;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * RoutingKey
     */
    private String routingKey;

    /**
     * 消息
     */
    private String context;
}
