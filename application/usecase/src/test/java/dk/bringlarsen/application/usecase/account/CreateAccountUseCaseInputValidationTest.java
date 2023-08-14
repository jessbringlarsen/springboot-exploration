package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseInputValidationTest extends ConstraintValidationTest {

    final String validId = UUID.randomUUID().toString();

    @Mock
    AccountRepository accountRepository;
    CreateAccountUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new CreateAccountUseCase(validator, accountRepository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testBlankName(String invalidName) {
        Input input = new Input(validId, invalidName);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "must not be blank", "name");
    }

    @ParameterizedTest
    @CsvSource({"INVALID"})
    @NullAndEmptySource
    void testInvalidCustomerId(String invalidId) {
        Input input = new Input(invalidId, validId);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "not valid", "customerId");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
