package com.example.estoquepreco.controller;

import com.example.estoquepreco.dto.StockDto;
import com.example.estoquepreco.enums.RabbitMQEnum;
import com.example.estoquepreco.service.RabbitMQService;
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
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity updateStock(@RequestBody StockDto stockDto) {

        this.rabbitMQService.sendMessage(RabbitMQEnum.QUEUE_STOCK.getDescription(), stockDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
