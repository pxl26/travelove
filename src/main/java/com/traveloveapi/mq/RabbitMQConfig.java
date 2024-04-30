package com.traveloveapi.mq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@EnableRabbit
public class RabbitMQConfig {
    @Bean
    public Queue initQueue() {
        return new Queue("feedback");
    }

    @Bean
    public Queue initQueue2() {
        return new Queue("booking");
    }
}
