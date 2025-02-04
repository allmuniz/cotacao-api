package com.project.api_cotacao.entities.user;

import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import jakarta.persistence.*;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;
    private Double principalBalance;

    @OneToOne(cascade = CascadeType.ALL)
    private WalletEntity wallet;

    public UserEntity(UserRequestDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.password= dto.password();
        this.principalBalance = 0.0;
        this.wallet = new WalletEntity("BRL");
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(Double principalBalance) {
        this.principalBalance = principalBalance;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }
}
