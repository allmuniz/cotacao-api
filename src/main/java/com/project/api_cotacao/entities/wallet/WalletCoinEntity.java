package com.project.api_cotacao.entities.wallet;

import com.project.api_cotacao.entities.coin.CoinEntity;
import jakarta.persistence.*;

@Entity
public class WalletCoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private CoinEntity coin;

    private Double balance;// Saldo individual para cada Wallet + Coin

    private Boolean notification;

    private Double valueNotification;

    public WalletCoinEntity(WalletEntity wallet, CoinEntity coin, Double balance) {
        this.wallet = wallet;
        this.coin = coin;
        this.balance = balance;
        this.notification = false;
        this.valueNotification = 0.0;
    }

    public WalletCoinEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    public CoinEntity getCoin() {
        return coin;
    }

    public void setCoin(CoinEntity coin) {
        this.coin = coin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public Double getValueNotification() {
        return valueNotification;
    }

    public void setValueNotification(Double valueNotification) {
        this.valueNotification = valueNotification;
    }
}
