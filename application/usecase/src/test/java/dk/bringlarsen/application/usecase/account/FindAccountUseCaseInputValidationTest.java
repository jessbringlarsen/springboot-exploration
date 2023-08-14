package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.account.FindAccountsUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FindAccountUseCaseInputValidationTest extends ConstraintValidationTest {

    @Mock
    AccountRepository accountRepository;
    FindAccountsUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new FindAccountsUseCase(100, validator, accountRepository);
    }

    @ParameterizedTest
    @CsvSource({"INVALID"})
    @NullAndEmptySource
    void testInvalidCustomerId(String invalidId) {
        Input input = new Input(invalidId, 1, 100);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "not valid", "customerId");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
