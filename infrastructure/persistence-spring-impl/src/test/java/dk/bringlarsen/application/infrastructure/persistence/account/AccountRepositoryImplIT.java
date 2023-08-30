package dk.bringlarsen.application.infrastructure.persistence.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.infrastructure.persistence.BaseRepositoryTest;
import dk.bringlarsen.application.infrastructure.persistence.customer.CustomerSpringDataRepository;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import dk.bringlarsen.application.infrastructure.persistence.transaction.TransactionMapperImpl;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.PersistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integrationtest")
@DataJpaTest(showSql = false)
@ExtendWith(SpringExtension.class)
class AccountRepositoryImplIT extends BaseRepositoryTest {

    @Autowired
    AccountSpringDataRepository springDataRepository;
    @Autowired
    CustomerSpringDataRepository customerSpringDataRepository;
    AccountRepositoryImpl repository;
    UUID customerId;


    @BeforeEach
    void setUp() {
        AccountMapperImpl mapper = new AccountMapperImpl(new TransactionMapperImpl());
        repository = new AccountRepositoryImpl(mapper, springDataRepository, customerSpringDataRepository);
        customerId = createCustomer();
    }

    @Test
    void testFindById() {
        Account account = createAccountWithCustomerId(customerId);

        Optional<Account> result = repository.findById(account.getId());

        assertTrue(result.isPresent());
        assertEquals(account.getId(), result.get().getId());
    }

    @Test
    void testFindByUnknownId() {
        Optional<Account> result = repository.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdAndCustomerId() {
        Account account = createAccountWithCustomerId(customerId);

        Optional<Account> result = repository.findByIdAndCustomerId(account.getId(), customerId);

        assertTrue(result.isPresent());
        assertEquals(account.getId(), result.get().getId());
    }

    @Test
    void testFindByIdWithUnknownId() {
        createAccountWithCustomerId(customerId);

        Optional<Account> result = repository.findByIdAndCustomerId(UUID.randomUUID(), customerId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdUnrelatedCustomer() {
        Account account = createAccountWithCustomerId(customerId);

        Optional<Account> result = repository.findByIdAndCustomerId(account.getId(), UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByCustomerId() {
        Account account = createAccountWithCustomerId(customerId);

        LinkedList<Account> result = new LinkedList<>(repository.findByCustomerId(customerId, getPageWithNo(0)));

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(account.getCustomerId(), result.getFirst().getCustomerId()),
            () -> assertEquals(account.getBalance(), result.getFirst().getBalance()),
            () -> assertEquals(account.getName(), result.getFirst().getName()));
    }

    @Test
    void testFindByCustomerIdPaged() {
        createAccountWithCustomerId(customerId);
        createAccountWithCustomerId(customerId);

        List<Account> firstPageResult = repository.findByCustomerId(customerId, getPageWithNo(0));
        List<Account> secondPageResult = repository.findByCustomerId(customerId, getPageWithNo(1));
        List<Account> thirdPageResult = repository.findByCustomerId(customerId, getPageWithNo(2));

        assertEquals(1, firstPageResult.size());
        assertEquals(1, secondPageResult.size());
        assertEquals(0, thirdPageResult.size());
    }

    @Test
    void testDeleteCustomer() {
        Account accountId = createAccountWithCustomerId(customerId);
        createAccountWithCustomerId(customerId);

        repository.delete(accountId.getId());
        synchronizeWithDatabase();

        List<Account> accounts = repository.findByCustomerId(customerId, new Pageable(2, 2));
        assertEquals(1, accounts.size());
    }

    @Test
    void testDeleteUnknownAccount() {
        UUID randomID = UUID.randomUUID();
        PersistException exception = assertThrows(PersistException.class, () -> repository.delete(randomID));

        assertTrue(exception.getMessage().contains("Unable to delete account. Could not find account with id"),
            "Expected exception message: \"" + exception.getMessage() + "\" to contain: \"Unable to delete\"");
    }

    @Test
    void testCreateWithUnknownCustomer() {
        UUID randomId = UUID.randomUUID();
        PersistException exception = assertThrows(PersistException.class, () -> createAccountWithCustomerId(randomId));

        assertTrue(exception.getMessage().contains("Unable to persist account. Could not find Customer with id"),
            "Expected exception message \"" + exception.getMessage() + "\" to contain \"Unable to persist account. Could not find Customer with id\"");
    }

    private UUID createCustomer() {
        CustomerEntity customer = new CustomerEntity().setName("Customer");
        entityManager.persist(customer);
        return customer.getId();
    }

    private Account createAccountWithCustomerId(UUID customerId) {
        Account account = new Account(null, customerId, "Account", List.of());
        account = repository.persist(account);
        synchronizeWithDatabase();
        return account;
    }

    private Pageable getPageWithNo(int no) {
        return new Pageable(1, 1).withPageNumber(no);
    }
}
