package dk.bringlarsen.application.infrastructure.persistence.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionEntity> transactions;

    public UUID getId() {
        return id;
    }

    public AccountEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public AccountEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public AccountEntity setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity account = (AccountEntity) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (customer != null ? !customer.equals(account.customer) : account.customer != null) return false;
        return transactions != null ? transactions.equals(account.transactions) : account.transactions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        return result;
    }
}
