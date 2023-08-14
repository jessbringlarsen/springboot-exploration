package dk.bringlarsen.application.usecase;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountBuilder {

    private UUID id;
    private UUID customerId;
    private final List<Transaction> transactions = new ArrayList<>();

    private AccountBuilder() {
        // Util
    }

    public static AccountBuilder createAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withCustomerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public AccountBuilder withABalanceOf(BigDecimal balance) {
        transactions.add(new Transaction(null,
            id,
            "SomeText",
            balance,
            ZonedDateTime.now()));
        return this;
    }

    public Account build() {
        return new Account(
            id,
            customerId,
            "AccountName",
            transactions);
    }
}
