package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.AccountRepository;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CreateAccountUseCase extends AbstractUseCase<CreateAccountUseCase.Input, Account> {

    private final AccountRepository repository;

    @Autowired
    public CreateAccountUseCase(Validator validator, AccountRepository repository) {
        super(validator);
        this.repository = repository;
    }

    public Account doExecute(Input input) {
        Account account = new Account(null, UUID.fromString(input.customerId()), input.name(), List.of());
        return repository.persist(account);
    }

    public record Input(@ValidId String customerId, @NotBlank String name) {
    }
}
