package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.usecase.account.GetAccountUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static dk.bringlarsen.application.usecase.AccountBuilder.createAccount;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAccountUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    AccountRepository repository;
    @InjectMocks
    GetAccountUseCase useCase;

    final UUID customerId = UUID.randomUUID();
    final UUID accountId = UUID.randomUUID();

    @Test
    void testGetAccount() {
        when(repository.findByIdAndCustomerId(accountId, customerId))
            .thenReturn(Optional.of(createAccount().withId(accountId).withCustomerId(customerId).build()));

        Optional<Account> result = useCase.execute(new Input(customerId.toString(), accountId.toString()));

        verify(repository).findByIdAndCustomerId(accountId, customerId);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetAccountWithUnknownAccountId() {
        when(repository.findByIdAndCustomerId(accountId, customerId)).thenReturn(Optional.empty());

        Optional<Account> result = useCase.execute(new Input(customerId.toString(), accountId.toString()));

        assertTrue(result.isEmpty());
    }
}
