package com.example.stockprice.connections;

import constant.RabbitmqConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConnection {

    private static final String NAME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitmqConnection(AmqpAdmin amqpAdmin) {

        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {

        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {

        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding relationship (Queue queue, DirectExchange exchange) {
       return new Binding(
               queue.getName(),
               Binding.DestinationType.QUEUE,
               exchange.getName(),
               queue.getName(),
               null);
    }

    @PostConstruct
    private void initRabbitMQ() {
        this.add(RabbitmqConstants.QUEUE_STOCK);
        this.add(RabbitmqConstants.QUEUE_PRICE);
    }

    private void add(String queue) {
        Queue newQueue = this.queue(queue);

        DirectExchange exchange = this.directExchange();

        Binding relationship = this.relationship(newQueue, exchange);

        //Criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(newQueue);

        //RabbitMQ vai ver que já existe e não irá criar de novo, pois estou usando uma exchange que já existe
        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(relationship);
    }
}
