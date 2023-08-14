package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.PersistException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    AccountRepository repository;
    @Mock
    Validator validator;
    @InjectMocks
    CreateAccountUseCase useCase;

    @Test
    void testCreateAccount() {
        useCase.execute(new Input(randomUUID().toString(), "AccountName"));

        verify(repository).persist(any(Account.class));
    }

    @Test
    void testFailureToCreateAccount() {
        when(repository.persist(any())).thenThrow(new PersistException("fail"));
        Input input = new Input(randomUUID().toString(), "AccountName");

        assertThrows(PersistException.class, () -> useCase.execute(input));
    }
}
