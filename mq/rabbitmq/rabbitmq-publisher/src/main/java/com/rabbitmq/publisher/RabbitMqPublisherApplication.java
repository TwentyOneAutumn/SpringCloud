package com.rabbitmq.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqPublisherApplication.class, args);
    }

}
