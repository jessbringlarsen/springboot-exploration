package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerControllerTest {

    @Mock
    DeleteCustomerUseCase useCase;
    MockMvc mockMvc;
    MockHttpServletRequestBuilder request;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new DeleteCustomerController(useCase))
            .build();

        request = MockMvcRequestBuilders
            .delete("/customers/1");
    }

    @Test
    void givenKnownCustomerIdExpectNoContent() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/customers/1");

        mockMvc.perform(request)
            .andExpect(status().isNoContent());
    }
}
