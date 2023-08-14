package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.usecase.transaction.DepositMoneyUseCase.Input;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositMoneyUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    DepositMoneyUseCase useCase;

    @Test
    void testDepositMoney() {
        Account account = createAccount().withABalanceOf(BigDecimal.ZERO).build();
        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));

        useCase.execute(requestToDepositAmount(BigDecimal.TEN));

        verify(transactionRepository).persist(any(Transaction.class));
    }

    private Input requestToDepositAmount(BigDecimal amount) {
        return new Input(randomUUID().toString(), "sometext", amount);
    }
}
