package com.project.api_cotacao.entities.coin;

import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    @OneToMany(mappedBy = "coin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletCoinEntity> walletCoins;
    
    public CoinEntity(String code, Double balance) {
        this.code = code;
    }
    
    public CoinEntity() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
