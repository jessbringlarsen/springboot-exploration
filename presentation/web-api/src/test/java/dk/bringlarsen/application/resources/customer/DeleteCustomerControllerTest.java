package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(DeleteCustomerController.class)
class DeleteCustomerControllerTest {

    @MockitoBean
    DeleteCustomerUseCase useCase;
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void givenKnownCustomerIdExpectNoContent() {
        assertThat(mockMvcTester.delete().uri("/customers/1"))
            .hasStatus(HttpStatus.NO_CONTENT);
    }
}
