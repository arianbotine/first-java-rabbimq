package com.example.estoquepreco.connections;

import com.example.estoquepreco.enums.RabbitMQEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {

    private static final String NAME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding relationship (Queue queue, DirectExchange exchange) {
       return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add() {
        Queue queueStock = this.queue(RabbitMQEnum.QUEUE_STOCK.getDescription());
        Queue queuePrice = this.queue(RabbitMQEnum.QUEUE_PRICE.getDescription());

        DirectExchange exchange = this.directExchange();

        Binding relationshipStock = this.relationship(queueStock, exchange);
        Binding relationshipPrice = this.relationship(queuePrice, exchange);

        //Criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(queueStock);
        this.amqpAdmin.declareQueue(queuePrice);

        //RabbitMQ vai ver que já existe e não irá criar de novo, pois estou usando uma exchange que já existe
        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(relationshipStock);
        this.amqpAdmin.declareBinding(relationshipPrice);
    }
}
