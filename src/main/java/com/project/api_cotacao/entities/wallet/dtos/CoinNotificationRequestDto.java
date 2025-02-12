package com.project.api_cotacao.entities.wallet.dtos;

import java.io.Serializable;

public record CoinNotificationRequestDto(String code, Double value) implements Serializable {
}
