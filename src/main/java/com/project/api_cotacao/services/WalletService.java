package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.coin.dtos.WalletCoinDto;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeCurrencieResponseDto;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeCurrencieResquestDto;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeDto;
import com.project.api_cotacao.entities.exchange.exceptions.ExchangeNotFoundException;
import com.project.api_cotacao.entities.transaction.TransactionEntity;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.entities.wallet.dtos.CoinNotificationRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.CoinNotificationResponseDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.entities.wallet.exceptions.AmountDepositedException;
import com.project.api_cotacao.repositories.WalletRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final CoinService coinService;
    private final CotacaoService cotacaoService;
    private final TransactionService transactionService;

    public WalletService(WalletRepository walletRepository, CoinService coinService, @Lazy CotacaoService cotacaoService,
                         TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.coinService = coinService;
        this.cotacaoService = cotacaoService;
        this.transactionService = transactionService;
    }

    public WalletEntity createWallet(UserEntity user) {
        WalletEntity wallet = new WalletEntity(user);
        walletRepository.save(wallet);

        coinService.createCoin("BRL", wallet, true);
        return wallet;
    }

    public ResponseEntity<WalletResponseDto> findWallet(UserEntity user) {
        List<CoinEntity> coins = coinService.getAllCoinsToUser(user);
        return ResponseEntity.ok(WalletResponseDto.fromWallet(coins));
    }

    public ResponseEntity<WalletCoinDto> handleTransaction(WalletEntity wallet, WalletCoinDto dto, TransactionType type) {
        boolean isDeposit = type == TransactionType.DEPOSIT;
        boolean isExchange = type == TransactionType.EXCHANGE;
        return ResponseEntity.ok().body(updateCoin(wallet, dto.code(), dto.balance(), isDeposit, isExchange));
    }

    public ResponseEntity<ExchangeCurrencieResponseDto> exchangeCurrencies(
            WalletEntity wallet, ExchangeCurrencieResquestDto dto) {

        CoinEntity sendCoin = coinService.getPrincipalCoin(wallet);
        CoinEntity receiveCoin = coinService.getCoinByWalletAndCode(wallet, dto.code());
        if (receiveCoin == null) receiveCoin = coinService.createCoin(dto.code(), wallet, false);

        ExchangeDto cotacao = cotacaoService.requestExchange(sendCoin.getCode(), receiveCoin.getCode())
                .orElseThrow(() -> new ExchangeNotFoundException("Cotação não encontrada."));

        BigDecimal sendAmount = BigDecimal.valueOf(dto.sendAmount());
        BigDecimal receiveAmount = sendAmount.multiply(BigDecimal.valueOf(cotacao.bid())).setScale(2, RoundingMode.HALF_UP);

        WalletCoinDto newSendCoinDto = updateCoin(wallet, sendCoin.getCode(), dto.sendAmount(), false, true);
        WalletCoinDto newReceiveCoinDto = updateCoin(wallet, receiveCoin.getCode(), receiveAmount.doubleValue(), true, true);

        TransactionEntity transaction = new TransactionEntity(TransactionType.EXCHANGE, wallet, receiveCoin, sendCoin,
                receiveAmount.doubleValue(), sendAmount.doubleValue(), LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        return ResponseEntity.ok(buildExchangeResponse(newSendCoinDto, newReceiveCoinDto));
    }

    public Double getPrincipalBalance(WalletEntity wallet) {
        CoinEntity principalCoin = coinService.getPrincipalCoin(wallet);
        return principalCoin.getBalance();
    }

    public ResponseEntity<WalletCoinDto> updatePrincipalCoin(WalletEntity wallet, String code) {
        CoinEntity oldCoin = coinService.getPrincipalCoin(wallet);
        oldCoin.setPrincipal(false);
        coinService.saveCoin(oldCoin);

        CoinEntity newCoin = coinService.getCoinByWalletAndCode(wallet, code);
        newCoin.setPrincipal(true);
        coinService.saveCoin(newCoin);

        CoinEntity newPrincipalCoin = coinService.getPrincipalCoin(wallet);
        return ResponseEntity.ok().body(new WalletCoinDto(newPrincipalCoin.getCode(), newPrincipalCoin.getBalance()));
    }

    public ResponseEntity<CoinNotificationResponseDto> notificationCoin(WalletEntity wallet, CoinNotificationRequestDto dto) {
        CoinEntity coin = coinService.getCoinByWalletAndCode(wallet, dto.code());
        coin.setNotification(true);
        coin.setValueNotification(dto.value());
        coinService.saveCoin(coin);
        return ResponseEntity.ok().body(new CoinNotificationResponseDto(coin.getCode(), coin.getNotification(), coin.getValueNotification()));
    }

    private WalletCoinDto updateCoin(WalletEntity wallet, String code, Double value, Boolean isDeposit, Boolean isExchange) {
        verificaValue(value);

        CoinEntity coin = coinService.getCoinByWalletAndCode(wallet, code);

        double newBalance = isDeposit ? coin.getBalance() + value : coin.getBalance() - value;
        coin.setBalance(newBalance);

        CoinEntity updatedCoin = coinService.saveCoin(coin);

        if (!isExchange){
            TransactionEntity transaction = isDeposit ? new TransactionEntity(TransactionType.DEPOSIT, wallet, coin, value, LocalDateTime.now()) :
                    new TransactionEntity(TransactionType.WITHDRAW, wallet, coin, value, LocalDateTime.now());
            transactionService.saveTransaction(transaction);
        }

        return new WalletCoinDto(updatedCoin.getCode(), updatedCoin.getBalance());
    }

    private void verificaValue(Double value){
        if (value <= 0) {
            throw new AmountDepositedException("Invalid deposited amount");
        }
    }

    private ExchangeCurrencieResponseDto buildExchangeResponse(WalletCoinDto sendCoin, WalletCoinDto receiveCoin) {
        return new ExchangeCurrencieResponseDto(sendCoin.code(), sendCoin.balance(),
                receiveCoin.code(), receiveCoin.balance());
    }
}