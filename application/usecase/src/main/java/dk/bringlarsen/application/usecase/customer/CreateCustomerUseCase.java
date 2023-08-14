package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.domain.service.CustomerRepository;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerUseCase extends AbstractUseCase<CreateCustomerUseCase.Input, Customer> {

    private final CustomerRepository repository;

    @Autowired
    public CreateCustomerUseCase(Validator validator, CustomerRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Customer doExecute(Input input) {
        return repository.persist(new Customer(input.customerName()));
    }

    public record Input(@NotBlank String customerName) {

    }
}
