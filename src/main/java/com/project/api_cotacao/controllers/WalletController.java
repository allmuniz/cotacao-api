package com.project.api_cotacao.controllers;

import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResponseDto;
import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResquestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PutMapping("/{type}")
    public ResponseEntity<WalletCoinResponseDto> handleTransaction(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable TransactionType type,
            @RequestBody WalletCoinRequestDto dto) {

        return walletService.handleTransaction(user.getWallet(), dto, type);
    }

    @PostMapping("/exchange/{receiveCoinId}")
    public ResponseEntity<ExchangeCurrencieResponseDto> exchangeWallet(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long receiveCoinId,
            @RequestBody ExchangeCurrencieResquestDto dto) {
        return walletService.exchangeCurrencies(user.getWallet(),receiveCoinId,dto);
    }

    @GetMapping("/balance")
    public Double getPrincipalBalance(@AuthenticationPrincipal UserEntity user) {
        return walletService.getPrincipalBalance(user.getWallet());
    }
}
