package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CreateCustomerController.class)
@Import(CustomerDTOMapperImpl.class)
class CreateCustomerControllerTest {

    @MockitoBean
    CreateCustomerUseCase useCase;
    @Autowired
    MockMvcTester mockMvcTester;
    MockHttpServletRequestBuilder request;

    @BeforeEach
    void setup() {
        request = MockMvcRequestBuilders
            .post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"CustomerName":"test"}
                """);
    }

    @Test
    void givenValidRequestExpectCustomerIsCreated() {
        when(useCase.execute(any(Input.class)))
            .thenReturn(new Customer("1", "test"));

        MvcTestResult result = mockMvcTester.perform(request);

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson()
            .hasPath("id")
            .hasPath("name");
        assertThat(result).bodyJson()
            .extractingPath("$._links.self.href").asString().contains("/customers/1");

    }

    @Test
    void givenMissingBodyExpectBadRequest() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/customers");

        mockMvcTester.perform(request).assertThat().hasStatus(HttpStatus.BAD_REQUEST);
    }
}
