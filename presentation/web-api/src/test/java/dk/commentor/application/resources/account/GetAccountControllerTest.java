package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapperImpl;
import dk.bringlarsen.application.usecase.account.GetAccountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GetAccountControllerTest {

    @Mock
    GetAccountUseCase useCase;
    MockMvc mockMvc;
    MockHttpServletRequestBuilder request;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new GetAccountController(useCase, new AccountDTOMapperImpl()))
            .build();

        request = MockMvcRequestBuilders
            .get("/customers/1/accounts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"balance\":\"0\"}");
    }

    @Test
    void givenValidRequestExpectAccountReturned() throws Exception {
        Account account = new Account(UUID.randomUUID(), UUID.randomUUID(), "test", List.of());
        when(useCase.execute(any(GetAccountUseCase.Input.class)))
            .thenReturn(Optional.of(account));

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.links[?(@.rel == 'self')].href")
                .value("http://localhost/customers/1/accounts/1"))
            .andExpect(jsonPath("id")
                .value(account.getId().toString()))
            .andExpect(jsonPath("customerId")
                .value(account.getCustomerId().toString()))
            .andExpect(jsonPath("name")
                .value(account.getName()))
            .andExpect(jsonPath("balance")
                .value(account.getBalance()));
    }
}
