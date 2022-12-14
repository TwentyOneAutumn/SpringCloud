package com.demo.kafka.Config;

import com.demo.kafka.DoMain.KafkaTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Array;
import java.util.Arrays;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Bean
    KafkaProducer<String,String> kafkaProducer(){
        Properties config = new Properties();
        // 生产端ID
        config.put(ProducerConfig.CLIENT_ID_CONFIG, "Producer1");
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        // Kafka集群地址 host1:port1,host2:port2 ....
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.111:9092");
        // key.deserializer 消息key序列化方式
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.deserializer 消息体序列化方式
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(config);
    }

    @Bean
    KafkaConsumer<String,String> kafkaConsumer(){
        Properties config = new Properties();
        // 消费端ID
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "Consumer1");
        // 组ID
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
        // Kafka集群地址 host1:port1,host2:port2 ....
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.111:9092");
        // key.deserializer 消息key序列化方式
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value.deserializer 消息体序列化方式
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置自动提交offset
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // auto.offset.reset
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(config);
        // 订阅Top
        kafkaConsumer.subscribe(Arrays.asList(KafkaTopic.TEST1));
        return kafkaConsumer;
    }
}
