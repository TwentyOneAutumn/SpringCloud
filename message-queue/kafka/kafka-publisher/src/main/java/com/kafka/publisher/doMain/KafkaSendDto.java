package com.kafka.publisher.doMain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KafkaSendDto {
    @NotBlank(message = "topic参数不能为空")
    private String topic;
    @NotBlank(message = "key参数不能为空")
    private String key;
    @NotBlank(message = "value参数不能为空")
    private String value;
}
