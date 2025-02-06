package com.project.api_cotacao.entities.wallet;

import com.project.api_cotacao.entities.coin.CoinEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "principal_coin_id")
    private CoinEntity principalCode;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletCoinEntity> walletCoins;

    public WalletEntity(CoinEntity principalCode) {
        this.principalCode = principalCode;
    }

    public WalletEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoinEntity getPrincipalCode() {
        return principalCode;
    }

    public void setPrincipalCode(CoinEntity principalCode) {
        this.principalCode = principalCode;
    }

    public List<WalletCoinEntity> getWalletCoins() {
        return walletCoins;
    }

    public void setWalletCoins(List<WalletCoinEntity> walletCoins) {
        this.walletCoins = walletCoins;
    }
}
