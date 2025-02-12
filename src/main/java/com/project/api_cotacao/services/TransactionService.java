package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.transaction.TransactionEntity;
import com.project.api_cotacao.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }
}
