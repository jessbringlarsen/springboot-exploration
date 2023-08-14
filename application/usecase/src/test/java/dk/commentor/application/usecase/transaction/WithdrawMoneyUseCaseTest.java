package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.usecase.UseCaseValidationException;
import dk.bringlarsen.application.usecase.transaction.WithdrawMoneyUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.TransactionRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static dk.bringlarsen.application.usecase.AccountBuilder.createAccount;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawMoneyUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    WithdrawMoneyUseCase useCase;

    @Test
    void testInsufficientFunds() {
        when(accountRepository.findById(any(UUID.class)))
            .thenReturn(Optional.of(createAccount().withABalanceOf(BigDecimal.ZERO).build()));
        Input input = requestToWithdrawAmount(BigDecimal.TEN);

        UseCaseValidationException exception = assertThrows(UseCaseValidationException.class,
            () -> useCase.execute(input));

        assertEquals("Insufficient funds!", exception.getMessage());
    }

    @Test
    void testWithdrawMoney() {
        when(accountRepository.findById(any(UUID.class)))
            .thenReturn(Optional.of(createAccount().withABalanceOf(BigDecimal.TEN).build()));

        useCase.execute(requestToWithdrawAmount(BigDecimal.TEN));

        verify(transactionRepository).persist(any(Transaction.class));
    }

    private Input requestToWithdrawAmount(BigDecimal amount) {
        return new Input(randomUUID().toString(), "sometext", amount);
    }
}
