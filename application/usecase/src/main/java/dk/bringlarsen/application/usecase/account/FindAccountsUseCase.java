package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.Pageable;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FindAccountsUseCase extends AbstractUseCase<FindAccountsUseCase.Input, List<Account>> {

    private final int maxPageSize;
    private final AccountRepository repository;

    @Autowired
    public FindAccountsUseCase(@Value("${findAccountsUseCase.maxPageSize:100}") int maxPageSize,
                               Validator validator,
                               AccountRepository repository) {
        super(validator);
        this.maxPageSize = maxPageSize;
        this.repository = repository;
    }

    @Override
    public List<Account> doExecute(Input input) {
        Pageable page = new Pageable(maxPageSize, input.size())
            .withPageNumber(input.pageNumber());
        return repository.findByCustomerId(UUID.fromString(input.customerId()), page);
    }

    public record Input(@ValidId String customerId, int size, int pageNumber) {

    }
}
