package com.kafka.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class KafkaPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaPublisherApplication.class, args);
        log.info("Kafka生产端服务启动成功");
    }

}
