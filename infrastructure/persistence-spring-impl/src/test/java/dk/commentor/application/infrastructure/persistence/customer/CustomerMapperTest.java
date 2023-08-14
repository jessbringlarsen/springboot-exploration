package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Nested
    class WhenMappingToEntityTest {

        @Test
        void testFromEntityWithValues() {
            CustomerEntity customerEntity = new CustomerEntity()
                .setId(UUID.randomUUID())
                .setName("Test");

            Customer customer = mapper.map(customerEntity);

            assertAll(() -> {
                assertEquals(customerEntity.getId().toString(), customer.getId());
                assertEquals(customerEntity.getName(), customer.getName());
            });
        }

        @Test
        void testFromEntityWithNullValues() {
            CustomerEntity customerEntity = new CustomerEntity()
                .setId(null)
                .setName(null);

            Customer customer = mapper.map(customerEntity);

            assertAll(() -> {
                assertNull(customer.getId());
                assertNull(customer.getName());
            });
        }
    }

    @Nested
    class WhenMappingFromEntityTest {
        @Test
        void testToEntityWithValues() {
            Customer customer = new Customer(UUID.randomUUID().toString(), "Test");

            CustomerEntity customerEntity = mapper.map(customer);

            assertAll(() -> {
                assertEquals(customer.getId(), customerEntity.getId().toString());
                assertEquals(customer.getName(), customerEntity.getName());
            });
        }

        @Test
        void testFromEntityWithNullValues() {
            Customer customer = new Customer(null, null);

            CustomerEntity customerEntity = mapper.map(customer);

            assertAll(() -> {
                assertNull(customerEntity.getId());
                assertNull(customerEntity.getName());
            });
        }
    }
}
