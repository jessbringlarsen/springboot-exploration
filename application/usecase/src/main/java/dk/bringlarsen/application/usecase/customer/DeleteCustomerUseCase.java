package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.validation.ValidId;
import dk.bringlarsen.domain.service.CustomerRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ***********************************
 * #HandsOn - Implement DeleteCustomer
 * ***********************************
 */
@Component
public class DeleteCustomerUseCase extends AbstractUseCase<DeleteCustomerUseCase.Input, Void> {

    private final CustomerRepository repository;

    @Autowired
    public DeleteCustomerUseCase(Validator validator, CustomerRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Void doExecute(Input input) {
        Optional<Customer> customer = repository.findById(input.id());
        if (customer.isEmpty()) {
            throw new UseCaseValidationException("Customer not found!");
        }
         repository.delete(input.id());
        return null;
    }

    public record Input(@ValidId String id) {

    }
}
