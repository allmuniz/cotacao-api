package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.coin.exceptions.CoinNotFoundException;
import com.project.api_cotacao.repositories.CoinRepository;
import org.springframework.stereotype.Service;

@Service
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public CoinEntity getCoinById(Long id) {
        return coinRepository.findById(id).orElseThrow(()-> new CoinNotFoundException("Coin not found"));
    }
}
