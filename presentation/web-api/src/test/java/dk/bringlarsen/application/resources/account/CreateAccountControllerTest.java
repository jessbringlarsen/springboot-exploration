package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapperImpl;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CreateAccountController.class)
@Import(AccountDTOMapperImpl.class)
class CreateAccountControllerTest {

    @MockitoBean
    private CreateAccountUseCase useCase;
    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    void givenValidRequestExpectAccountIsCreated() {
        when(useCase.execute(any()))
            .thenReturn(new Account(UUID.randomUUID(), UUID.randomUUID(), "test", List.of()));

        assertThat(mockMvcTester.post().uri("/customers/1/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"name":"savings"}
                """)).hasStatus(HttpStatus.CREATED);
    }

    @Test
    void givenMissingBodyExpectBadRequest() {
        assertThat(mockMvcTester.post().uri("/customers/1/accounts")
            .contentType(MediaType.APPLICATION_JSON)).hasStatus(HttpStatus.BAD_REQUEST);
    }
}
