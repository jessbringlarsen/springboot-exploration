package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase.Input;
import dk.bringlarsen.domain.service.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseInputValidationTest extends ConstraintValidationTest {

    @Mock
    CustomerRepository repository;
    CreateCustomerUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new CreateCustomerUseCase(validator, repository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testBlankName(String invalidName) {
        Input input = new Input(invalidName);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "must not be blank", "customerName");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
