package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.coin.CoinEntity;
import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import com.project.api_cotacao.entities.wallet.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCoinRepository extends JpaRepository<WalletCoinEntity,Long> {
    WalletCoinEntity findByWalletAndCoin(WalletEntity wallet, CoinEntity coin);
}
