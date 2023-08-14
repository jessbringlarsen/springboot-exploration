package dk.bringlarsen.domain.service;

import dk.bringlarsen.application.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    List<Transaction> findByAccountId(UUID id, Pageable page);

    Transaction persist(Transaction transaction);
}
