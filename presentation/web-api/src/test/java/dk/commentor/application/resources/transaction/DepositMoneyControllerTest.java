package dk.bringlarsen.application.resources.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.mapper.TransactionDTOMapperImpl;
import dk.bringlarsen.application.usecase.transaction.DepositMoneyUseCase;
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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DepositMoneyControllerTest {

    @Mock
    DepositMoneyUseCase useCase;
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new DepositMoneyController(useCase, new TransactionDTOMapperImpl()))
            .build();
    }

    @Test
    void givenValidRequestExpectValidResponse() throws Exception {
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), "text", BigDecimal.TEN, ZonedDateTime.now());
        when(useCase.execute(any(DepositMoneyUseCase.Input.class))).thenReturn(transaction);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/accounts/1/deposit")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"Text":"some text", "Amount":5}
                """);

        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    void givenMissingBodyExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/accounts/1/deposit");

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }
}
