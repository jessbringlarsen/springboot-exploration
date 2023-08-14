package dk.bringlarsen.application.usecase.account;

import dk.bringlarsen.application.usecase.account.FindAccountsUseCase.Input;
import dk.bringlarsen.domain.service.AccountRepository;
import dk.bringlarsen.domain.service.Pageable;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindAccountsUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    AccountRepository repository;
    FindAccountsUseCase useCase;
    @Captor
    ArgumentCaptor<Pageable> repositoryCaptor;

    @BeforeEach
    void setUp() {
        useCase = new FindAccountsUseCase(100, validator, repository);
    }

    @Test
    void testFindAccounts() {
        useCase.execute(new Input(randomUUID().toString(), 1, 0));

        verify(repository).findByCustomerId(any(UUID.class), repositoryCaptor.capture());
        assertAll(
            () -> assertTrue(repositoryCaptor.getValue().sortAscending()),
            () -> assertEquals(0, repositoryCaptor.getValue().getPage()),
            () -> assertEquals(1, repositoryCaptor.getValue().getSize()),
            () -> assertEquals("id", repositoryCaptor.getValue().getSortFields()[0])
        );
    }
}
