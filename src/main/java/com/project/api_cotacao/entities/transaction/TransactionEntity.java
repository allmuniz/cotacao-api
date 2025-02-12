package com.project.api_cotacao.entities.transaction;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import com.project.api_cotacao.entities.wallet.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false) // Relacionamento com Wallet
    private WalletEntity wallet;

    @ManyToOne
    @JoinColumn(name = "receiver_coin_id") // Moeda que recebe a transação
    private CoinEntity receiverCoin;

    @ManyToOne
    @JoinColumn(name = "sender_coin_id") // Moeda que envia a transação
    private CoinEntity senderCoin;

    private double receiverValue;

    private double senderValue;

    private LocalDateTime date;

    public TransactionEntity(TransactionType type, WalletEntity wallet, CoinEntity receiverCoin, CoinEntity senderCoin,
                             double receiverValue, double senderValue, LocalDateTime date) {
        this.type = type;
        this.wallet = wallet;
        this.receiverCoin = receiverCoin;
        this.senderCoin = senderCoin;
        this.receiverValue = receiverValue;
        this.senderValue = senderValue;
        this.date = date;
    }

    public TransactionEntity(TransactionType type, WalletEntity wallet, CoinEntity coin, double value,
                             LocalDateTime date) {
        this.type = type;
        this.wallet = wallet;
        if (type.equals(TransactionType.DEPOSIT)){
            this.receiverCoin = coin;
            this.receiverValue = value;
        }
        if (type.equals(TransactionType.WITHDRAW)){
            this.senderCoin = coin;
            this.senderValue = value;
        }
        this.date = date;
    }

    public TransactionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    public CoinEntity getReceiverCoin() {
        return receiverCoin;
    }

    public void setReceiverCoin(CoinEntity receiverCoin) {
        this.receiverCoin = receiverCoin;
    }

    public CoinEntity getSenderCoin() {
        return senderCoin;
    }

    public void setSenderCoin(CoinEntity senderCoin) {
        this.senderCoin = senderCoin;
    }

    public double getReceiverValue() {
        return receiverValue;
    }

    public void setReceiverValue(double receiverValue) {
        this.receiverValue = receiverValue;
    }

    public double getSenderValue() {
        return senderValue;
    }

    public void setSenderValue(double senderValue) {
        this.senderValue = senderValue;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

