package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapperImpl;
import dk.bringlarsen.application.usecase.customer.FindAllCustomersUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(FindAllCustomersController.class)
@Import(CustomerDTOMapperImpl.class)
class FindAllCustomersControllerTest {

    @MockitoBean
    FindAllCustomersUseCase useCase;
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void givenValidRequestExpectValidResponse() {
        when(useCase.execute(anyInt(), anyInt(), anyBoolean(), anyString()))
            .thenReturn(singletonList(new Customer("1", "test")));

        MvcTestResult result = mockMvcTester.perform(MockMvcRequestBuilders.get("/customers"));

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.length()").asNumber().isEqualTo(1);
        assertThat(result).bodyJson().extractingPath("$[0].id").asString().isEqualTo("1");
        assertThat(result).bodyJson().extractingPath("$[0].name").asString().isEqualTo("test");
    }

    @Test
    void givenNoCustomersExpectValidResponse() {
        when(useCase.execute(anyInt(), anyInt(), anyBoolean(), anyString()))
            .thenReturn(Collections.emptyList());

        MvcTestResult result = mockMvcTester.perform(MockMvcRequestBuilders.get("/customers"));

        assertThat(result)
            .hasStatusOk()
            .bodyJson().extractingPath("$.length()").isEqualTo(0);
    }
}
