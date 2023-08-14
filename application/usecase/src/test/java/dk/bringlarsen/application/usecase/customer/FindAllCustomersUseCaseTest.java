package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.Pageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindAllCustomersUseCaseTest {

    @Mock
    CustomerRepository repository;
    FindAllCustomersUseCase useCase;
    @Captor
    ArgumentCaptor<Pageable> repositoryCaptor;

    @BeforeEach
    void setUp() {
        useCase = new FindAllCustomersUseCase(100, repository);
    }

    @Test
    void testFindAllCustomers() {
        useCase.execute(0, 1, true, "id");

        verify(repository).findAll(repositoryCaptor.capture());
        assertAll(
            () -> assertTrue(repositoryCaptor.getValue().sortAscending()),
            () -> assertEquals(0, repositoryCaptor.getValue().getPage()),
            () -> assertEquals(1, repositoryCaptor.getValue().getSize()),
            () -> assertEquals("id", repositoryCaptor.getValue().getSortFields()[0])
        );
    }
}
