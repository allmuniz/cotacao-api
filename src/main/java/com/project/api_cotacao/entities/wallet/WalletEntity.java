package com.project.api_cotacao.entities.wallet;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String principalCode;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletCoinEntity> walletCoins;

    public WalletEntity(String principalCode) {
        this.principalCode = principalCode;
    }

    public WalletEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrincipalCode() {
        return principalCode;
    }

    public void setPrincipalCode(String principalCode) {
        this.principalCode = principalCode;
    }

    public List<WalletCoinEntity> getWalletCoins() {
        return walletCoins;
    }

    public void setWalletCoins(List<WalletCoinEntity> walletCoins) {
        this.walletCoins = walletCoins;
    }
}
