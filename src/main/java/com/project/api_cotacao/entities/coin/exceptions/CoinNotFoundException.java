package com.project.api_cotacao.entities.coin.exceptions;

public class CoinNotFoundException extends RuntimeException {
    public CoinNotFoundException(String message) {
        super(message);
    }
}
