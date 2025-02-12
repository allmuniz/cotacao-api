package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.wallet.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

}
