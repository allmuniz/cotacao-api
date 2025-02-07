package com.project.api_cotacao.entities.exchange.exceptions;

public class ExchangeNotFoundException extends RuntimeException {
    public ExchangeNotFoundException(String message) {
        super(message);
    }
}
