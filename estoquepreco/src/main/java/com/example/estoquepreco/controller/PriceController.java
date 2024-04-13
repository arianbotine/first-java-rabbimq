package com.example.estoquepreco.controller;

import dto.PriceDto;
import enums.RabbitMQEnum;
import com.example.estoquepreco.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "price")
public class PriceController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity updatePrice(@RequestBody PriceDto priceDto) {

        this.rabbitMQService.sendMessage(RabbitMQEnum.QUEUE_PRICE.getDescription(), priceDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
