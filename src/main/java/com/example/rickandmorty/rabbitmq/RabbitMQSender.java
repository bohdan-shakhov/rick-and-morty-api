package com.example.rickandmorty.rabbitmq;

import com.example.rickandmorty.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQSender {
    private final AmqpTemplate rabbitTemplate;
    private final Queue queue;

    public void send(User user) {
        rabbitTemplate.convertAndSend(queue.getName(), user);
        log.info("Sending message to the Queue: " + user.toString());
    }
}
