package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.coin.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<CoinEntity, Long> {
}
