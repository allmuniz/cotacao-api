package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinResponseDto;
import com.project.api_cotacao.entities.wallet.exceptions.AmountDepositedException;
import com.project.api_cotacao.repositories.WalletCoinRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WalletCoinService {

    private final WalletCoinRepository walletCoinRepository;
    private final WalletService walletService;
    private final CoinService coinService;

    public WalletCoinService(WalletCoinRepository walletCoinRepository, WalletService walletService, CoinService coinService) {
        this.walletCoinRepository = walletCoinRepository;
        this.walletService = walletService;
        this.coinService = coinService;
    }

    public void createWalletCoin(WalletEntity wallet, CoinEntity coin) {
        WalletEntity newWallet = walletService.getWalletById(wallet.getId());
        CoinEntity newCoin = coinService.getCoinById(coin.getId());

        WalletCoinEntity walletCoin = new WalletCoinEntity(newWallet, newCoin, 0.0);
        walletCoinRepository.save(walletCoin);
    }

    public ResponseEntity<WalletCoinResponseDto> updateWalletCoin(WalletCoinEntity coin, Double value, Boolean isDeposit) {
        if (value <= 0) {
            throw new AmountDepositedException("Invalid deposited amount");
        }

        double newBalance = isDeposit ? coin.getBalance() + value : coin.getBalance() - value;
        coin.setBalance(newBalance);

        WalletCoinEntity updatedCoin = walletCoinRepository.save(coin);
        WalletCoinResponseDto responseDto = new WalletCoinResponseDto(updatedCoin.getCoin().getCode(), updatedCoin.getBalance());

        return ResponseEntity.ok(responseDto);
    }

    public WalletCoinEntity getWalletCoinByWalletAndCoin(WalletEntity wallet, CoinEntity coin) {
        WalletEntity newWallet = walletService.getWalletById(wallet.getId());
        CoinEntity newCoin = coinService.getCoinById(coin.getId());

        return walletCoinRepository.findByWalletAndCoin(newWallet, newCoin);
    }
}
