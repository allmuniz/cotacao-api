package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeDto;
import com.project.api_cotacao.entities.exchange.exceptions.ExchangeNotFoundException;
import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResponseDto;
import com.project.api_cotacao.entities.wallet.dtos.ExchangeCurrencieResquestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletCoinResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.entities.wallet.exceptions.WalletNotFoundException;
import com.project.api_cotacao.repositories.WalletRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletCoinService walletCoinService;
    private final CoinService coinService;
    private final CotacaoService cotacaoService;

    public WalletService(WalletRepository walletRepository, @Lazy WalletCoinService walletCoinService, CoinService coinService, CotacaoService cotacaoService) {
        this.walletRepository = walletRepository;
        this.walletCoinService = walletCoinService;
        this.coinService = coinService;
        this.cotacaoService = cotacaoService;
    }

    public ResponseEntity<WalletCoinResponseDto> handleTransaction(Long walletId, WalletCoinRequestDto dto, TransactionType type) {
        WalletEntity wallet = getWalletById(walletId);
        WalletCoinEntity coin = walletCoinService.getWalletCoinByWalletAndCoin(wallet, wallet.getPrincipalCode());

        boolean isDeposit = type == TransactionType.DEPOSIT;

        return ResponseEntity.ok().body(walletCoinService.updateWalletCoin(coin, dto.balance(), isDeposit));
    }

    public ResponseEntity<ExchangeCurrencieResponseDto> exchangeCurrencies(
            Long walletId, Long receiveCoinId, ExchangeCurrencieResquestDto dto) {

        WalletEntity wallet = getWalletById(walletId);
        CoinEntity receiveCoin = coinService.getCoinById(receiveCoinId);

        WalletCoinEntity sendWalletCoin = walletCoinService.getWalletCoinByWalletAndCoin(wallet, wallet.getPrincipalCode());
        WalletCoinEntity receiveWalletCoin = walletCoinService.getWalletCoinByWalletAndCoin(wallet, receiveCoin);

        ExchangeDto cotacao = requestExchange(wallet.getPrincipalCode().getCode(), receiveCoin.getCode())
                .orElseThrow(() -> new ExchangeNotFoundException("Cotação não encontrada."));

        BigDecimal sendAmount = BigDecimal.valueOf(dto.sendAmount());
        BigDecimal receiveAmount = sendAmount.multiply(BigDecimal.valueOf(cotacao.bid())).setScale(2, RoundingMode.HALF_UP);

        WalletCoinResponseDto newSendCoinDto = walletCoinService.updateWalletCoin(sendWalletCoin, dto.sendAmount(), false);

        if (receiveWalletCoin == null) {
            receiveWalletCoin = walletCoinService.createWalletCoin(wallet, receiveCoin);
        }

        WalletCoinResponseDto newReceiveCoinDto = walletCoinService.updateWalletCoin(receiveWalletCoin, receiveAmount.doubleValue(), true);

        return ResponseEntity.ok(buildExchangeResponse(newSendCoinDto, newReceiveCoinDto));
    }

    private ExchangeCurrencieResponseDto buildExchangeResponse(WalletCoinResponseDto sendCoin, WalletCoinResponseDto receiveCoin) {
        return new ExchangeCurrencieResponseDto(sendCoin.code(), sendCoin.balance(), receiveCoin.code(), receiveCoin.balance());
    }

    public Optional<ExchangeDto> requestExchange(String sender, String receiver) {
        Map<String, Map<String, Object>> cotacaoOriginal = cotacaoService.getCotacao(sender + "-" + receiver);

        if (cotacaoOriginal != null && !cotacaoOriginal.isEmpty()) {
            Map<String, Object> valores = cotacaoOriginal.values().iterator().next();

            if (valores.containsKey("bid") && valores.containsKey("ask")) {
                BigDecimal bid = new BigDecimal(valores.get("bid").toString()).setScale(2, RoundingMode.HALF_UP);
                BigDecimal ask = new BigDecimal(valores.get("ask").toString()).setScale(2, RoundingMode.HALF_UP);

                return Optional.of(new ExchangeDto(bid.doubleValue(), ask.doubleValue()));
            }
        }
        return Optional.empty();
    }

    public WalletEntity getWalletById(Long id) {
    return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }
}
