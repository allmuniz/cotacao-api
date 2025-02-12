package com.project.api_cotacao.entities.coin;

import com.project.api_cotacao.entities.wallet.WalletEntity;
import jakarta.persistence.*;

@Entity
public class CoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Double balance;// Saldo individual para cada Coin

    private Boolean notification;

    private Double valueNotification;

    private Boolean isPrincipal;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    public CoinEntity(String code) {
        this.code = code;
        this.balance = 0.0;
        this.notification = false;
        this.valueNotification = 0.0;
        this.isPrincipal = false;
    }

    public CoinEntity(String code, WalletEntity wallet, Boolean isPrincipal) {
        this.code = code;
        this.wallet = wallet;
        this.balance = 0.0;
        this.notification = false;
        this.valueNotification = 0.0;
        this.isPrincipal = isPrincipal;
    }
    
    public CoinEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(Boolean principal) {
        isPrincipal = principal;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }
}
