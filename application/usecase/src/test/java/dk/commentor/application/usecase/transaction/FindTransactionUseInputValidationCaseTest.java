package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.transaction.FindTransactionUseCase.Input;
import dk.bringlarsen.domain.service.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FindTransactionUseInputValidationCaseTest extends ConstraintValidationTest {

    @Mock
    TransactionRepository repository;
    FindTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindTransactionUseCase(validator, repository);
    }

    @ParameterizedTest
    @CsvSource("INVALID")
    @NullAndEmptySource
    void testInvalidAccountId(String accountId) {
        Input input = new Input(accountId, 0, 1);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "not valid", "accountId");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
