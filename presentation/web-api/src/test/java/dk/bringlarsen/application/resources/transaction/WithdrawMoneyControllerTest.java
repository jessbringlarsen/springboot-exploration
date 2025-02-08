package dk.bringlarsen.application.resources.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.mapper.TransactionDTOMapperImpl;
import dk.bringlarsen.application.usecase.transaction.WithdrawMoneyUseCase;
import dk.bringlarsen.application.usecase.transaction.WithdrawMoneyUseCase.Input;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(WithdrawMoneyController.class)
@Import(TransactionDTOMapperImpl.class)
class WithdrawMoneyControllerTest {

    @MockitoBean
    WithdrawMoneyUseCase useCase;

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void givenValidRequestExpectValidResponse() {
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), "text", BigDecimal.TEN, ZonedDateTime.now());
        when(useCase.execute(any(Input.class))).thenReturn(transaction);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/accounts/1/withdraw")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"Text":"some text", "Amount":5}
                """);

        mockMvcTester.perform(request)
            .assertThat().hasStatusOk();
    }

    @Test
    void givenMissingBodyExpectBadRequest() {
        mockMvcTester.post().uri("/accounts/1/withdraw")
            .assertThat().hasStatus(HttpStatus.BAD_REQUEST);
    }
}
