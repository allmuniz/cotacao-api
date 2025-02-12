package com.project.api_cotacao.entities.wallet.dtos;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.coin.dtos.CoinResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public record WalletResponseDto(List<CoinResponseDto> coins) {

    public static WalletResponseDto fromWallet(List<CoinEntity> coins) {
        List<CoinResponseDto> coinDtos = coins.stream()
                .map(coin -> new CoinResponseDto(coin.getCode(), coin.getBalance(), coin.getPrincipal()))
                .collect(Collectors.toList());

        return new WalletResponseDto(coinDtos);
    }
}
