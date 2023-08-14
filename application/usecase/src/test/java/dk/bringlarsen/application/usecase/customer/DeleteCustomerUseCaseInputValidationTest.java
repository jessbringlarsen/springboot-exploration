package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase.Input;
import dk.bringlarsen.domain.service.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseInputValidationTest extends ConstraintValidationTest {

    @Mock
    CustomerRepository repository;
    DeleteCustomerUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new DeleteCustomerUseCase(validator, repository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidCustomerId(String invalidId) {
        Input input = new Input(invalidId);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "not valid", "id");
    }

    @Test
    void testUnknownCustomer() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        Input input = new Input(UUID.randomUUID().toString());

        UseCaseValidationException violation = triggerViolation(input);

        assertEquals("Customer not found!", violation.getMessage());
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
