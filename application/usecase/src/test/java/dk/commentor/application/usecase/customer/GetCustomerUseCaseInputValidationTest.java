package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase.Input;
import dk.bringlarsen.domain.service.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GetCustomerUseCaseInputValidationTest extends ConstraintValidationTest {

    @Mock
    CustomerRepository repository;
    GetCustomerUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new GetCustomerUseCase(validator, repository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidCustomerId(String invalidId) {
        UseCaseValidationException violation = triggerViolation(new Input(invalidId));

        assertViolations(violation, "not valid", "id");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
