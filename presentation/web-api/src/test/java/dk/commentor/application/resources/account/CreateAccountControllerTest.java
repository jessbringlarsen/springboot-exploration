package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapperImpl;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreateAccountControllerTest {

    @Mock
    CreateAccountUseCase useCase;
    MockMvc mockMvc;
    MockHttpServletRequestBuilder request;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new CreateAccountController(useCase, new AccountDTOMapperImpl()))
            .build();

        request = MockMvcRequestBuilders
            .post("/customers/1/accounts/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"balance\":\"0\"}");
    }

    @Test
    void givenValidRequestExpectAccountIsCreated() throws Exception {
        when(useCase.execute(any(CreateAccountUseCase.Input.class)))
            .thenReturn(new Account(UUID.randomUUID(), UUID.randomUUID(), "test", List.of()));

        mockMvc.perform(request)
            .andExpect(status().isCreated());
    }

    @Test
    void givenMissingBodyExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/customers/1/accounts/");

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }
}
