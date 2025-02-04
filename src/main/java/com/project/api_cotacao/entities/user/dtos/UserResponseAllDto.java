package com.project.api_cotacao.entities.user.dtos;

import com.project.api_cotacao.entities.coin.dtos.CoinResponseDto;
import com.project.api_cotacao.entities.user.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record UserResponseAllDto(String name, Double principalBalance, List<CoinResponseDto> coins) {

    public static UserResponseAllDto fromEntity(UserEntity user) {
        List<CoinResponseDto> coinDtos = Optional.ofNullable(user.getWallet().getWalletCoins())
                .orElse(Collections.emptyList()) // Se for null, retorna uma lista vazia
                .stream()
                .map(coin -> new CoinResponseDto(coin.getCoin().getCode(), coin.getBalance()))
                .collect(Collectors.toList());

        return new UserResponseAllDto(user.getName(), user.getPrincipalBalance(), coinDtos);
    }
}
