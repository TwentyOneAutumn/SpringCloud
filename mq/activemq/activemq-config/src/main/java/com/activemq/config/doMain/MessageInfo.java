package com.activemq.config.doMain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo implements Serializable {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String topic;

    /**
     * 消息对象
     */
    @NotNull(message = "消息对象不能为空")
    private Object message;
}
