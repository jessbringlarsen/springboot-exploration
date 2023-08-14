package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.FindAllCustomersUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FindAllCustomersControllerTest {

    @Mock
    FindAllCustomersUseCase useCase;
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new FindAllCustomersController(useCase, new CustomerDTOMapperImpl()))
            .build();
    }

    @Test
    void givenValidRequestExpectValidResponse() throws Exception {
        when(useCase.execute(anyInt(), anyInt(), anyBoolean(), anyString()))
            .thenReturn(singletonList(new Customer("1", "test")));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].name").value("test"));
    }

    @Test
    void givenNoCustomersExpectValidResponse() throws Exception {
        when(useCase.execute(anyInt(), anyInt(), anyBoolean(), anyString()))
            .thenReturn(Collections.emptyList());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/customers");

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }
}
