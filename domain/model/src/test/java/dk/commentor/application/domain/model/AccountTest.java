package dk.bringlarsen.application.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @ParameterizedTest
    @NullAndEmptySource
    void givenEmptyAccountExpectWithdrawNotAllowed(List<Transaction> transactions) {
        Account account = createAccountWithTransactions(transactions);

        assertFalse(account.canWithdrawMoney(BigDecimal.TEN));
    }

    @Test
    void givenInsufficientFundsExpectWithdrawNotAllowed() {
        Transaction transaction = getTransactionWithAmount(BigDecimal.ONE);
        Account account = createAccountWithTransactions(List.of(transaction));

        assertFalse(account.canWithdrawMoney(BigDecimal.TEN));
    }

    @Test
    void givenSufficientFundsExpectWithdrawAllowed() {
        Transaction transaction = getTransactionWithAmount(BigDecimal.TEN);
        Account account = createAccountWithTransactions(List.of(transaction));

        assertTrue(account.canWithdrawMoney(BigDecimal.ONE));
    }

    private Transaction getTransactionWithAmount(BigDecimal amount) {
        return new Transaction(UUID.randomUUID(), UUID.randomUUID(), "text", amount, ZonedDateTime.now());
    }

    private Account createAccountWithTransactions(List<Transaction> transactions) {
        return new Account(UUID.randomUUID(), UUID.randomUUID(), "name", transactions);
    }
}
