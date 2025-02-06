package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.entities.wallet.exceptions.WalletNotFoundException;
import com.project.api_cotacao.repositories.WalletRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletCoinService walletCoinService;

    public WalletService(WalletRepository walletRepository, @Lazy WalletCoinService walletCoinService) {
        this.walletRepository = walletRepository;
        this.walletCoinService = walletCoinService;
    }

    public ResponseEntity<WalletCoinResponseDto> handleTransaction(Long walletId, WalletCoinRequestDto dto, TransactionType type) {
        WalletEntity wallet = getWalletById(walletId);
        WalletCoinEntity coin = walletCoinService.getWalletCoinByWalletAndCoin(wallet, wallet.getPrincipalCode());

        boolean isDeposit = type == TransactionType.DEPOSIT;
        return walletCoinService.updateWalletCoin(coin, dto.balance(), isDeposit);
    }

    public WalletEntity getWalletById(Long id) {
    return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }
}
