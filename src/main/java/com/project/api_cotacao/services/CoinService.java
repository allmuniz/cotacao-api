package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.coin.exceptions.CoinNotFoundException;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.repositories.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public CoinEntity createCoin(String code, WalletEntity wallet, Boolean isPrincipal) {
        CoinEntity coin = new CoinEntity(code, wallet, isPrincipal);
        coinRepository.save(coin);
        return coin;
    }

    public CoinEntity saveCoin(CoinEntity coin) {
        coinRepository.save(coin);
        return coin;
    }

    public CoinEntity getCoinById(Long id) {
        return coinRepository.findById(id).orElseThrow(()-> new CoinNotFoundException("Coin not found"));
    }

    public List<CoinEntity> getAllCoinsToWallet(WalletEntity wallet) {
        return coinRepository.findAllByWallet(wallet);
    }

    public List<CoinEntity> getAllCoinsToUser(UserEntity user) {
        return coinRepository.findAllByUserId(user.getId());
    }

    public CoinEntity getCoinByWalletAndCode(WalletEntity wallet, String code) {
        return coinRepository.findByWalletAndCode(wallet, code);
    }

    public CoinEntity getPrincipalCoin(WalletEntity wallet) {
        return coinRepository.findPrincipalCoinByWallet(wallet).orElseThrow(()-> new CoinNotFoundException("Coin not found"));
    }
}
