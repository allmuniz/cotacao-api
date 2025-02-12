package com.project.api_cotacao.entities.wallet;

import com.project.api_cotacao.entities.user.UserEntity;
import jakarta.persistence.*;

@Entity
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public WalletEntity(UserEntity user) {
        this.user = user;
    }

    public WalletEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    /*public List<CoinEntity> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinEntity> coins) {
        this.coins = coins;
    }*/
}
