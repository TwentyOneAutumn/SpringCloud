package com.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:jdbc.properties"})
@SpringBootApplication
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}
