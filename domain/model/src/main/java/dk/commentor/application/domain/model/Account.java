package dk.bringlarsen.application.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Account {

    private final UUID id;
    private final UUID customerId;

    private final String name;
    private final List<Transaction> transactions;

    public Account(UUID id, UUID customerId, String name, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
        this.transactions = transactions;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public boolean canWithdrawMoney(BigDecimal amount) {
        return getBalance().subtract(amount.abs()).signum() >= 0;
    }

    public BigDecimal getBalance() {
        if (transactions == null) {
            return BigDecimal.ZERO;
        }
        return transactions.stream()
            .map(Transaction::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
