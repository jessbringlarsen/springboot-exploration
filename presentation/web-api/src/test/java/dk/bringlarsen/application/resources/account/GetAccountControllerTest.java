package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapperImpl;
import dk.bringlarsen.application.usecase.account.GetAccountUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(GetAccountController.class)
@Import(AccountDTOMapperImpl.class)
class GetAccountControllerTest {

    @MockitoBean
    GetAccountUseCase useCase;
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void givenValidRequestExpectAccountReturned() {
        when(useCase.execute(any(GetAccountUseCase.Input.class)))
            .thenReturn(Optional.of(new Account(UUID.randomUUID(), UUID.randomUUID(), "test", List.of())));

        MvcTestResult result = mockMvcTester.get().uri("/customers/1/accounts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"balance":"0"}
                """).exchange();

        assertThat(result)
            .hasStatusOk();
        assertThat(result).bodyJson()
            .hasPath("id")
            .hasPath("customerId")
            .hasPath("name")
            .hasPath("balance");
        assertThat(result).bodyJson()
            .extractingPath("$._links.self.href").asString().contains("/customers/1/accounts/1");
    }
}
