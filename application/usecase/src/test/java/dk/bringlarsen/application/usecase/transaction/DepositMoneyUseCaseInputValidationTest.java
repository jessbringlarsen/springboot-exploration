package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.usecase.ConstraintValidationTest;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.transaction.DepositMoneyUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositMoneyUseCaseInputValidationTest extends ConstraintValidationTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountRepository accountRepository;
    DepositMoneyUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new DepositMoneyUseCase(validator, transactionRepository, accountRepository);
    }

    @ParameterizedTest
    @CsvSource("INVALID")
    @NullAndEmptySource
    void testInvalidAccountId(String accountId) {
        Input input = new Input(accountId, "Sometext", BigDecimal.TEN);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "not valid", "accountId");
    }

    @Test
    void testUnknownAccountId() {
        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Input input = new Input(UUID.randomUUID().toString(), "Sometext", BigDecimal.TEN);

        UseCaseValidationException violation = triggerViolation(input);

        assertEquals("Account not found.", violation.getMessage());
    }

    @Test
    void testInvalidText() {
        Input input = new Input(UUID.randomUUID().toString(), "", BigDecimal.TEN);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "must not be blank", "text");
    }

    @Test
    void testInvalidAmount() {
        Input input = new Input(UUID.randomUUID().toString(), "sometext", null);

        UseCaseValidationException violation = triggerViolation(input);

        assertViolations(violation, "must not be null", "amount");
    }

    private UseCaseValidationException triggerViolation(Input input) {
        return assertThrows(UseCaseValidationException.class, () -> useCase.execute(input));
    }
}
