package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapperImpl;
import dk.bringlarsen.application.usecase.account.FindAccountsUseCase;
import dk.bringlarsen.application.usecase.account.FindAccountsUseCase.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FindAccountsControllerTest {

    @Mock
    FindAccountsUseCase useCase;
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new FindAccountsController(useCase, new AccountDTOMapperImpl()))
            .build();
    }

    @Test
    void givenValidRequestExpectValidResponse() throws Exception {
        Account account = new Account(UUID.randomUUID(), UUID.randomUUID(), "name", List.of());
        when(useCase.execute(any(Input.class)))
            .thenReturn(singletonList(account));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers/1/accounts");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(account.getId().toString()))
            .andExpect(jsonPath("$[0].customerId").value(account.getCustomerId().toString()))
            .andExpect(jsonPath("$[0].name").value(account.getName()))
            .andExpect(jsonPath("$[0].balance").value(account.getBalance()));
    }
}
