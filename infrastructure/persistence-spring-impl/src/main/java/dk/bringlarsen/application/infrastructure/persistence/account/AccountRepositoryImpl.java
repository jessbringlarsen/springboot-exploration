package dk.bringlarsen.application.infrastructure.persistence.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.infrastructure.persistence.customer.CustomerSpringDataRepository;
import dk.bringlarsen.application.infrastructure.persistence.model.AccountEntity;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.PersistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountMapper mapper;
    private final AccountSpringDataRepository accountRepository;
    private final CustomerSpringDataRepository customerRepository;

    @Autowired
    public AccountRepositoryImpl(AccountMapper mapper,
                                 AccountSpringDataRepository accountRepository,
                                 CustomerSpringDataRepository customerRepository) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Account> findByIdAndCustomerId(UUID id, UUID customerId) {
        return accountRepository.findByIdAndCustomerId(id, customerId)
            .map(mapper::map);
    }

    @Override
    public List<Account> findByCustomerId(UUID id, Pageable page) {
        return accountRepository.findByCustomerId(id,
                PageRequest.of(page.getPage(), page.getSize())).stream()
            .map(mapper::map)
            .toList();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id)
            .map(mapper::map);
    }

    @Override
    public void delete(UUID id) {
        Optional<AccountEntity> entity = accountRepository.findById(id);
        if (entity.isEmpty()) {
            throw new PersistException("Unable to delete account. Could not find account with id: " + id);
        }
        accountRepository.delete(entity.get());
    }

    @Override
    public Account persist(Account account) {
        Optional<CustomerEntity> customer = customerRepository.findById(account.getCustomerId());
        if (customer.isEmpty()) {
            throw new PersistException("Unable to persist account. Could not find Customer with id: " + account.getCustomerId());
        }
        try {
            AccountEntity entity = accountRepository.save(mapper.map(account, customer.get()));
            return mapper.map(entity);
        } catch (DataAccessException e) {
            throw new PersistException("Unable to persist account", e);
        }
    }
}
