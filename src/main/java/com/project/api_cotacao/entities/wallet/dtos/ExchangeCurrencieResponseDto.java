package com.project.api_cotacao.entities.wallet.dtos;

public record ExchangeCurrencieResponseDto(String sendCoin, Double sendAmount, String receiveCoin, Double receiveAmount) {
}
