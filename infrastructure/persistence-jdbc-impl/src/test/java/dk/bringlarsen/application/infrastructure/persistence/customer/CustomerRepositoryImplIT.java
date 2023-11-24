package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.domain.service.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("integrationtest")
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerRepositoryImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void test() {
        Customer customer = customerRepository.persist(new Customer("test"));

        assertEquals("test", customer.getName());
    }
}
