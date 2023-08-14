package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase.Input;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CreateCustomerControllerTest {

    @Mock
    CreateCustomerUseCase useCase;
    MockMvc mockMvc;
    MockHttpServletRequestBuilder request;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new CreateCustomerController(useCase, new CustomerDTOMapperImpl()))
            .build();

        request = MockMvcRequestBuilders
            .post("/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"CustomerName":"test"}
                """);
    }

    @Test
    void givenValidRequestExpectCustomerIsCreated() throws Exception {
        when(useCase.execute(any(Input.class)))
            .thenReturn(new Customer("1", "test"));

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.links[?(@.rel == 'self')].href")
                .value("http://localhost/customers/1"))
            .andExpect(jsonPath("id")
                .value("1"))
            .andExpect(jsonPath("name")
                .value("test"));
    }

    @Test
    void givenMissingBodyExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/customers/");

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }
}
