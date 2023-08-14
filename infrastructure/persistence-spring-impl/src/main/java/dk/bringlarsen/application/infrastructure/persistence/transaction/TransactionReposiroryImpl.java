package dk.bringlarsen.application.infrastructure.persistence.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.infrastructure.persistence.account.AccountSpringDataRepository;
import dk.bringlarsen.application.infrastructure.persistence.model.AccountEntity;
import dk.bringlarsen.application.infrastructure.persistence.model.TransactionEntity;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.PersistException;
import dk.bringlarsen.domain.service.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionReposiroryImpl implements TransactionRepository {

    private final TransactionSpringDataRepository transactionRepository;
    private final AccountSpringDataRepository accountRepository;
    private final TransactionMapper mapper;

    @Autowired
    public TransactionReposiroryImpl(AccountSpringDataRepository accountRepository, TransactionSpringDataRepository transactionRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Transaction> findByAccountId(UUID id, Pageable page) {
        return mapper.map(transactionRepository.findByAccountId(id, PageRequest.of(page.getPage(), page.getSize())));
    }

    @Override
    public Transaction persist(Transaction transaction) {
        Optional<AccountEntity> account = accountRepository.findById(transaction.accountId());
        if (account.isEmpty()) {
            throw new PersistException("Unable to persist transaction. Account with id " + transaction.accountId() + " not found");
        }
        try {
            TransactionEntity entity = transactionRepository.save(mapper.map(transaction, account.get()));
            return mapper.map(entity);
        } catch (DataAccessException e) {
            throw new PersistException("Unable to persist transaction", e);
        }
    }
}
