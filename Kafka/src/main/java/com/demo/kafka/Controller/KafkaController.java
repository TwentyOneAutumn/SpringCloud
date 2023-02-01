package com.demo.kafka.Controller;

import com.demo.Core.DoMain.AjaxResult;
import com.demo.kafka.DoMain.KafkaMsg;
import com.demo.kafka.DoMain.KafkaSendDto;
import com.demo.kafka.DoMain.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class KafkaController {

    @Autowired
    KafkaProducer<String,String> kafkaProducer;

    @Autowired
    KafkaConsumer<String, String> kafkaConsumer;

    /**
     * 发送消息
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/send")
    public AjaxResult sendMsg(@Valid KafkaSendDto dto){
        // 判断topic是否存在
        if(!KafkaTopic.authTopic(dto.getTopic())){
            return AjaxResult.error("Topic不存在");
        }
        // 生产者发发送消息
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(dto.getTopic(),dto.getKey(),dto.getValue());
        kafkaProducer.send(producerRecord);
        return AjaxResult.success("消息发送成功");
    }

    @GetMapping("/read")
    public AjaxResult readMsg(){
        // 消费者接收消息
        ConsumerRecords<String, String> poll = kafkaConsumer.poll(100);
        ArrayList<KafkaMsg> list = new ArrayList<>();
        for (ConsumerRecord<String, String> record : poll){
            list.add(new KafkaMsg(record.topic(),record.offset(),record.key(),record.value()));
            String valueStr = record.value();
        }
        return AjaxResult.success(list);
    }
}
