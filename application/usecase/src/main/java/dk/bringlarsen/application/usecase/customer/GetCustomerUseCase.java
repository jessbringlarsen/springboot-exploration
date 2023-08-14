package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.CustomerRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetCustomerUseCase extends AbstractUseCase<GetCustomerUseCase.Input, Optional<Customer>> {

    private final CustomerRepository repository;

    @Autowired
    public GetCustomerUseCase(Validator validator, CustomerRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Optional<Customer> doExecute(Input input) {
        return repository.findById(input.id());
    }

    public record Input(@ValidId String id) {

    }
}
