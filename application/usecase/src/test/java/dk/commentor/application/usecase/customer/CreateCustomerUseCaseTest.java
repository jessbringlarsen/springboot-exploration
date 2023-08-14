package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase.Input;
import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.PersistException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    CustomerRepository repository;
    @InjectMocks
    CreateCustomerUseCase useCase;

    @Test
    void testCreateCustomer() {
        useCase.execute(new Input("Test"));

        verify(repository).persist(any(Customer.class));
    }

    @Test
    void testFailureToCreateCustomer() {
        when(repository.persist(any(Customer.class))).thenThrow(new PersistException("Failure"));
        Input input = new Input("Test");

        assertThrows(PersistException.class, () -> useCase.execute(input));
    }
}
