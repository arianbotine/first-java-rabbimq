package com.example.stockprice.controller;

import dto.PriceDto;
import constant.RabbitmqConstants;
import com.example.stockprice.service.RabbitmqService;
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
    private RabbitmqService rabbitMQService;

    @PutMapping
    private ResponseEntity updatePrice(@RequestBody PriceDto priceDto) {

        this.rabbitMQService.sendMessage(RabbitmqConstants.QUEUE_PRICE, priceDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
