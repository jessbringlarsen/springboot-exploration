package dk.bringlarsen.application.infrastructure.persistence.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "transaction")
@Table(name = "transaction_table")
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private ZonedDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    public UUID getId() {
        return id;
    }

    public TransactionEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public TransactionEntity setText(String text) {
        this.text = text;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionEntity setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ZonedDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionEntity setTransactionDate(ZonedDateTime transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public TransactionEntity setAccount(AccountEntity account) {
        this.account = account;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(id, that.id) && text.equals(that.text) && amount.equals(that.amount) && transactionDate.equals(that.transactionDate) && account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, amount, transactionDate, account);
    }
}
