package com.example.rickandmorty.rabbitmq;

import com.example.rickandmorty.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {

    @RabbitHandler
    public void receiver(User user) {
        log.info("User listener invoked - Consuming message with User Id : " +
                user.getId());
    }
}
