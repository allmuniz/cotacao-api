package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.transaction.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
