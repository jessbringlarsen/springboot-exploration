package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.usecase.transaction.FindTransactionUseCase.Input;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.TransactionRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindTransactionUseCaseTest {

    @Mock
    Validator validator;
    @Mock
    TransactionRepository repository;
    @InjectMocks
    FindTransactionUseCase useCase;

    @Test
    void testValidAccountId() {
        useCase.execute(new Input(randomUUID().toString(), 0, 1));

        verify(repository).findByAccountId(any(UUID.class), any(Pageable.class));
    }
}
