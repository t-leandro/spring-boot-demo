package com.example.senders;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender implements Sender {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    // TODO : must be serializable?
    public<T> String send(T request) {
        return (String) rabbitTemplate.convertSendAndReceive(queueName, request);
    }
}
