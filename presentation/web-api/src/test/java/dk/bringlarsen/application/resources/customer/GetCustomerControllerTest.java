package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GetCustomerControllerTest {

    @Mock
    GetCustomerUseCase useCase;
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new GetCustomerController(useCase, new CustomerDTOMapperImpl()))
            .build();
    }

    @Test
    void givenValidRequestExpectCustomer() throws Exception {
        when(useCase.execute(any(Input.class))).thenReturn(Optional.of(new Customer("1", "test")));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers/1");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.links[?(@.rel == 'self')].href")
                .value("http://localhost/customers/1"))
            .andExpect(jsonPath("id")
                .value("1"))
            .andExpect(jsonPath("name")
                .value("test"));
    }

    @Test
    void givenMissingIdExpectStatusNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers/");

        mockMvc.perform(request)
            .andExpect(status().isNotFound());
    }

    @Test
    void givenUnknownIdExpectStatusNotFound() throws Exception {
        when(useCase.execute(any(Input.class))).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers/1");

        mockMvc.perform(request)
            .andExpect(status().isNotFound());
    }
}
