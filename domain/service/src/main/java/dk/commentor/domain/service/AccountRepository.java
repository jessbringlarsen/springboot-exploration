package dk.bringlarsen.domain.service;

import dk.bringlarsen.application.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> findByIdAndCustomerId(UUID id, UUID customerId);

    List<Account> findByCustomerId(UUID id, Pageable page);

    Optional<Account> findById(UUID id);

    void delete(UUID id);

    Account persist(Account account);
}
