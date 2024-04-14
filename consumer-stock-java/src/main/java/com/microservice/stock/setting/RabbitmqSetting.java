package com.microservice.stock.setting;

import com.microservice.stock.exception.HandlerException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqSetting {

    @Bean
    public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

        //Determina quantas mensagens simultaneas podem ser armazenadas no buffer do consumo da fila
        factory.setPrefetchCount(3);
        //factory.setGlobalQos(true);

        //Quantas threads por queue?
        //factory.setConsumersPerQueue(2);

        factory.setErrorHandler(new HandlerException());

        return factory;
    }

}
