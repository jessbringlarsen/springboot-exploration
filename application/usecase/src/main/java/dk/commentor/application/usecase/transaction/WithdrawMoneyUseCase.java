package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.TransactionRepository;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class WithdrawMoneyUseCase extends AbstractUseCase<WithdrawMoneyUseCase.Input, Transaction> {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public WithdrawMoneyUseCase(Validator validator,
                                TransactionRepository transactionRepository,
                                AccountRepository accountRepository) {
        super(validator);
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction doExecute(Input input) {
        Transaction transaction = input.getTransaction();
        Optional<Account> account = accountRepository.findById(transaction.accountId());
        if (account.isEmpty()) {
            throw new UseCaseValidationException("Account not found.");
        }
        if (!account.get().canWithdrawMoney(transaction.amount())) {
            throw new UseCaseValidationException("Insufficient funds!");
        }
        return transactionRepository.persist(transaction);
    }

    public static class Input {
        @ValidId
        private final String accountId;
        @NotBlank
        private final String text;
        @NotNull
        private final BigDecimal amount;

        public Input(String accountId, String text, BigDecimal amount) {
            this.accountId = accountId;
            this.text = text;
            this.amount = amount;
        }

        Transaction getTransaction() {
            return new Transaction(null,
                UUID.fromString(accountId),
                text,
                amount.abs().negate(),
                ZonedDateTime.now());
        }
    }
}
