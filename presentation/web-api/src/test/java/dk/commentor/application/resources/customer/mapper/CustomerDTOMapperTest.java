package dk.bringlarsen.application.resources.customer.mapper;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.model.CustomerDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerDTOMapperTest {

    private final CustomerDTOMapper mapper = new CustomerDTOMapperImpl();

    @Test
    void dtoMapperTest() {
        Customer from = new Customer(UUID.randomUUID().toString(), "test");

        CustomerDTO to = mapper.map(from);

        assertEquals(from.getId(), to.getId());
        assertEquals(from.getName(), to.getName());
    }
}
