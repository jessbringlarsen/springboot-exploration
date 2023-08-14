package dk.bringlarsen.application.resources.account.mapper;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.account.model.AccountDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDTOMapperTest {

    private final AccountDTOMapper mapper = new AccountDTOMapperImpl();

    @Test
    void mapTest() {
        Account from = createAccount();

        AccountDTO to = mapper.map(from);

        verifyMapping(from, to);
    }

    @Test
    void mapListTest() {
        List<Account> from = List.of(createAccount());

        List<AccountDTO> to = mapper.map(from);

        assertEquals(1, to.size());
        verifyMapping(from.get(0), to.get(0));
    }

    private Account createAccount() {
        return new Account(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "account",
            List.of(createTransaction()));
    }

    private Transaction createTransaction() {
        return new Transaction(UUID.randomUUID(), UUID.randomUUID(), "text", BigDecimal.TEN, ZonedDateTime.now());
    }

    private void verifyMapping(Account from, AccountDTO to) {
        assertAll(
            () -> assertEquals(from.getId(), to.id()),
            () -> assertEquals(from.getCustomerId(), to.customerId()),
            () -> assertEquals(from.getName(), to.name()),
            () -> assertEquals(from.getBalance(), to.balance()));
    }
}
