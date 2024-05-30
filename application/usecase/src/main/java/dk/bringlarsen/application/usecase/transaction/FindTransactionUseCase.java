package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.TransactionRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FindTransactionUseCase extends AbstractUseCase<FindTransactionUseCase.Input, List<Transaction>> {

    @Value("${usecase.findtransaction.maxpagesize:100}")
    private int maxPageSize;
    private final TransactionRepository repository;

    @Autowired
    public FindTransactionUseCase(Validator validator, TransactionRepository repository) {
        super(validator);
        this.repository = repository;
    }

    public List<Transaction> doExecute(Input input) {
        return repository.findByAccountId(
            input.getAccountId(),
            new Pageable(maxPageSize, input.pageSize).withPageNumber(input.page()));
    }

    public record Input(@ValidId String accountId, int page, int pageSize) {

        public UUID getAccountId() {
            return UUID.fromString(accountId);
        }
    }
}
