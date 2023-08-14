package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase.Input;
import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.PersistException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    CustomerRepository repository;
    @InjectMocks
    DeleteCustomerUseCase useCase;

    @BeforeEach
    void setUp() {
        when(repository.findById("id")).thenReturn(Optional.of(new Customer("name")));
    }

    @Test
    void testDeleteCustomer() {
        useCase.execute(new Input("id"));

        verify(repository).delete("id");
    }

    @Test
    void testFailureToDeleteCustomer() {
        doThrow(new PersistException("failure")).when(repository).delete("id");
        Input input = new Input("id");

        assertThrows(PersistException.class, () -> useCase.execute(input));
    }
}
