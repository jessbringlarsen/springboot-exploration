package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.AbstractUseCase;
import dk.bringlarsen.domain.service.CustomerRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerUseCase extends AbstractUseCase<CreateCustomerUseCase.Input, Customer> {

    private final CustomerRepository repository;

    private final ObservationRegistry observationRegistry;

    @Autowired
    public CreateCustomerUseCase(Validator validator, CustomerRepository repository, ObservationRegistry observationRegistry) {
        super(validator);
        this.repository = repository;
        this.observationRegistry = observationRegistry;
    }

    @Override
    public Customer doExecute(Input input) {
        Observation observation = Observation.start("usecase.customer.create", observationRegistry);
        try (Observation.Scope scope = observation.openScope()) {
            return repository.persist(new Customer(input.customerName()));
        } finally {
            observation.stop();
        }
    }

    public record Input(@NotBlank String customerName) {

    }
}
