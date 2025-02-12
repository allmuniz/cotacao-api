package com.project.api_cotacao.entities.exchange.dtos;

public record ExchangeCurrencieResponseDto(String sendCoin, Double sendAmount, String receiveCoin, Double receiveAmount) {
}
