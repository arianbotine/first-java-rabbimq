package com.microservice.stock.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.RabbitmqConstants;
import dto.StockDto;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitmqConstants.QUEUE_STOCK)
public class ConsumerStock {

    @RabbitHandler
    private void consumer(String message) throws JsonProcessingException {
        StockDto stockDto = new ObjectMapper().readValue(message, StockDto.class);

        System.out.println(stockDto.productCode);
        System.out.println(stockDto.quantity);
        System.out.println("-----------------------------------------");
    }
}
