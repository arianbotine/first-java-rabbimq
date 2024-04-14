package com.example.stockprice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(String queueName, Object message) {
        this.rabbitTemplate.convertAndSend(queueName, this.convertToJson(message));

    }

    private String convertToJson(Object message) {
        try {
            return this.objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
