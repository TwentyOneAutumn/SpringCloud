package com.kafka.consumer.doMain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMsg {
    private String topic;
    private Long offset;
    private String key;
    private String value;

    public static KafkaMsg get(String topic,Long offset,String key,String value){
        return new KafkaMsg(topic,offset,key,value);
    }
}
