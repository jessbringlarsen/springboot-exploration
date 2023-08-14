package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.domain.service.CustomerRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GetCustomerUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    CustomerRepository repository;
    @InjectMocks
    GetCustomerUseCase useCase;

    @Test
    void testGetCustomer() {
        String customerId = randomUUID().toString();

        useCase.execute(new GetCustomerUseCase.Input(customerId));

        verify(repository).findById(customerId);
    }
}
