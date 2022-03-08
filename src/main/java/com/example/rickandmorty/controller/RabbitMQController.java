package com.example.rickandmorty.controller;

import com.example.rickandmorty.entity.User;
import com.example.rickandmorty.rabbitmq.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/rabbitmq")
@RequiredArgsConstructor
public class RabbitMQController {
    private final RabbitMQSender rabbitMQSender;

    @PostMapping("/sender")
    public String producer(@RequestBody User user) {
        rabbitMQSender.send(user);
        return "Message sent to the RabbitMQ Queue Successfully";
    }
}
