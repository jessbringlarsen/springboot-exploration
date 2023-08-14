package dk.bringlarsen.application.infrastructure.persistence.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.infrastructure.persistence.BaseRepositoryTest;
import dk.bringlarsen.application.infrastructure.persistence.account.AccountSpringDataRepository;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@ExtendWith(SpringExtension.class)
class TransactionRepositoryImplTest extends BaseRepositoryTest {

    private static final UUID ACCOUNT_ID = UUID.fromString("3d895f65-0baf-49fc-8edd-ea01374a4dfd");

    @Autowired
    AccountSpringDataRepository accountSpringDataRepository;
    @Autowired
    TransactionSpringDataRepository transactionSpringDataRepository;
    TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransactionReposiroryImpl(accountSpringDataRepository,
            transactionSpringDataRepository,
            new TransactionMapperImpl());
    }

    @Test
    void testFindByAccountId() {
        repository.persist(new Transaction(null, ACCOUNT_ID, "initial", new BigDecimal("10.5"), ZonedDateTime.now()));
        synchronizeWithDatabase();

        List<Transaction> result = repository.findByAccountId(ACCOUNT_ID, new Pageable(1, 1));

        assertEquals(1, result.size());
        assertEquals(ACCOUNT_ID, result.get(0).accountId());
    }
}


