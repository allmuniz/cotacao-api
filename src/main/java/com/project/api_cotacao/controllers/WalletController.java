package com.project.api_cotacao.controllers;

import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResponseDto;
import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResquestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PutMapping("/{type}/{walletId}")
    public ResponseEntity<WalletCoinResponseDto> handleTransaction(
            @PathVariable Long walletId,
            @PathVariable TransactionType type,
            @RequestBody WalletCoinRequestDto dto) {

        return walletService.handleTransaction(walletId, dto, type);
    }

    @PostMapping("/exchange/{walletId}/{receiveCoinId}")
    public ResponseEntity<ExchangeCurrencieResponseDto> exchangeWallet(
            @PathVariable Long walletId,
            @PathVariable Long receiveCoinId,
            @RequestBody ExchangeCurrencieResquestDto dto) {
        return walletService.exchangeCurrencies(walletId,receiveCoinId,dto);
    }
}
