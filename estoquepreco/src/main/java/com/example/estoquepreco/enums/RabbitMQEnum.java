package com.example.estoquepreco.enums;

public enum RabbitMQEnum {
    QUEUE_STOCK("STOCK"),
    QUEUE_PRICE("PRICE");

    private String description;

    RabbitMQEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
