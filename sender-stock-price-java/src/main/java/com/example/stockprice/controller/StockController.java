package com.example.stockprice.controller;

import dto.StockDto;
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
@RequestMapping(value = "stock")
public class StockController {

    @Autowired
    private RabbitmqService rabbitMQService;

    @PutMapping
    private ResponseEntity updateStock(@RequestBody StockDto stockDto) {

        this.rabbitMQService.sendMessage(RabbitmqConstants.QUEUE_STOCK, stockDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
