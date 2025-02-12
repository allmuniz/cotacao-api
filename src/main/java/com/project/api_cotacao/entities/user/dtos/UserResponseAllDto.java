package com.project.api_cotacao.entities.user.dtos;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.coin.dtos.CoinResponseDto;
import com.project.api_cotacao.entities.user.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public record UserResponseAllDto(String name, List<CoinResponseDto> coins) {

    public static UserResponseAllDto fromEntity(UserEntity user, List<CoinEntity> coins) {
        List<CoinResponseDto> coinDtos = coins.stream()
                .map(coin -> new CoinResponseDto(coin.getCode(), coin.getBalance(), coin.getPrincipal()))
                .collect(Collectors.toList());

        return new UserResponseAllDto(user.getName(), coinDtos);
    }

}
