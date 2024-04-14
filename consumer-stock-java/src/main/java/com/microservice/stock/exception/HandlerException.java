package com.microservice.stock.exception;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class HandlerException implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        String queueName = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
        System.out.println(queueName);

        String errorMessage = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());
        System.out.println(errorMessage);

        System.out.println(t.getCause().getMessage());

        //Logar no ElasticSearch
        //Logar no Cloud Watch(AWS)
        //...

        throw new AmqpRejectAndDontRequeueException("Falha fatal, não deve retornar para a fila");
    }
}
