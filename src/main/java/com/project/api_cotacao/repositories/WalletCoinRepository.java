package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.wallet.WalletCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletCoinRepository extends JpaRepository<WalletCoinEntity,Long> {
}
