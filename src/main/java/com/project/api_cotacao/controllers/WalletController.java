package com.project.api_cotacao.controllers;

import com.project.api_cotacao.entities.coin.dtos.WalletCoinDto;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeCurrencieResponseDto;
import com.project.api_cotacao.entities.exchange.dtos.ExchangeCurrencieResquestDto;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.wallet.dtos.CoinNotificationRequestDto;
import com.project.api_cotacao.entities.wallet.dtos.CoinNotificationResponseDto;
import com.project.api_cotacao.entities.wallet.dtos.WalletResponseDto;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import com.project.api_cotacao.services.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet", description = "Wallet Manager")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/")
    @Operation(description = "Endpoint responsavel por buscar a carteira do usuário",
            summary = "Busca a carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na busca da carteira"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<WalletResponseDto> getWallet(@AuthenticationPrincipal UserEntity user) {
        return walletService.findWallet(user);
    }

    @PutMapping("/transation/{type}")
    @Operation(description = "Endpoint responsavel por fazer deposito e saque",
            summary = "Deposito/Saque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na transação"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<WalletCoinDto> handleTransaction(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable TransactionType type,
            @RequestBody WalletCoinDto dto) {

        return walletService.handleTransaction(user.getWallet(), dto, type);
    }

    @PostMapping("/exchange")
    @Operation(description = "Endpoint responsavel por fazer o câmbio entre as moedas",
            summary = "Câmbio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Câmbio efetuado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro no câmbio"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ExchangeCurrencieResponseDto> exchangeWallet(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody ExchangeCurrencieResquestDto dto) {
        return walletService.exchangeCurrencies(user.getWallet(),dto);
    }

    @GetMapping("/balance")
    @Operation(description = "Endpoint responsavel por buscar a moeda principal da carteira",
            summary = "Moeda princial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro em encontrar a moeda"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Double getPrincipalBalance(@AuthenticationPrincipal UserEntity user) {
        return walletService.getPrincipalBalance(user.getWallet());
    }

    @PutMapping("/{coin}")
    @Operation(description = "Endpoint responsavel por atualizar a moeda principal da carteira",
            summary = "Atualizar moeda princial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro em atualizar a moeda"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<WalletCoinDto> updatePrincipalCoin(@AuthenticationPrincipal UserEntity user,
                                                 @PathVariable String coin) {
        return walletService.updatePrincipalCoin(user.getWallet(), coin);
    }

    @PutMapping("/notification")
    @Operation(description = "Endpoint responsavel por habilitar a notificação de valor",
            summary = "Notifica sobre moeda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro em executar a notificação"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CoinNotificationResponseDto> notificationCoin(@AuthenticationPrincipal UserEntity user,
                                                                        @RequestBody CoinNotificationRequestDto dto) {
        return walletService.notificationCoin(user.getWallet(), dto);
    }
}
