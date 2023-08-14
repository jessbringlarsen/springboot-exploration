package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.AccountRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetAccountUseCase extends AbstractUseCase<GetAccountUseCase.Input, Optional<Account>> {

    private final AccountRepository repository;

    @Autowired
    public GetAccountUseCase(Validator validator, AccountRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Optional<Account> doExecute(Input input) {
        UUID accountId = UUID.fromString(input.accountId);
        UUID customerId = UUID.fromString(input.customerId);
        return repository.findByIdAndCustomerId(accountId, customerId);
    }

    public record Input(@ValidId String customerId, @ValidId String accountId) {

    }
}
