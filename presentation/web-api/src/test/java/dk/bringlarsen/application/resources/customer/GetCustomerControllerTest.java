package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase.Input;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(GetCustomerController.class)
@Import(CustomerDTOMapperImpl.class)
class GetCustomerControllerTest {

    @MockitoBean
    GetCustomerUseCase useCase;
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void givenValidRequestExpectCustomer() {
        when(useCase.execute(any(Input.class))).thenReturn(Optional.of(new Customer("1", "test")));

        MvcTestResult result = mockMvcTester.get().uri("/customers/1").exchange();

        assertThat(result)
            .hasStatusOk();
        assertThat(result).bodyJson()
            .hasPath("id")
            .hasPath("name");
        assertThat(result).bodyJson()
            .extractingPath("$._links.self.href").asString().contains("/customers/1");
    }

    @Test
    void givenMissingIdExpectStatusNotFound() {
        mockMvcTester.get().uri("/customers")
            .assertThat().hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenUnknownIdExpectStatusNotFound() {
        when(useCase.execute(any(Input.class))).thenReturn(Optional.empty());

        mockMvcTester.get().uri("/customers/1")
            .assertThat().hasStatus(HttpStatus.NOT_FOUND);
    }
}
