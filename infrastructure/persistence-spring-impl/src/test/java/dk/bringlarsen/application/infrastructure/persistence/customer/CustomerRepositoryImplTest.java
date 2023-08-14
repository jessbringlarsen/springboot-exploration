package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.infrastructure.persistence.BaseRepositoryTest;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.PersistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(showSql = false)
@Tag("integrationtest")
@ExtendWith(SpringExtension.class)
class CustomerRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    CustomerSpringDataRepository springDataRepository;
    CustomerRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new CustomerRepositoryImpl(springDataRepository, new CustomerMapper());
    }

    @Test
    void testFindById() {
        Customer customer = persistCustomerWithName("Test");

        Optional<Customer> optionalCustomer = repository.findById(customer.getId());

        assertAll(
            () -> assertTrue(optionalCustomer.isPresent()),
            () -> assertNotNull(optionalCustomer.get().getId()),
            () -> assertEquals("Test", optionalCustomer.get().getName())
        );
    }

    @Test
    void testNameNotNull() {
        Customer customer = new Customer(null);
        PersistException exception = assertThrows(PersistException.class, () -> repository.persist(customer));

        assertThat(exception)
            .rootCause()
            .hasMessageContaining("not-null property");
    }

    @Test
    void testFindAll() {
        persistANumberOfCustomers(4);

        List<Customer> customers = repository.findAll(new Pageable(10, 2)
            .withSortFields("name")
            .withSortDirectionAscending(true));

        assertThat(customers)
            .isSortedAccordingTo(comparing(Customer::getName));
    }

    @Test
    void testFindAllPaged() {
        persistANumberOfCustomers(4);

        List<Customer> customers = repository.findAll(new Pageable(10, 2));

        assertThat(customers)
            .hasSize(2);
    }

    private void persistANumberOfCustomers(int count) {
        for (int index = 1; index <= count; index++) {
            persistCustomerWithName(String.valueOf(index));
        }
    }

    private Customer persistCustomerWithName(String name) {
        Customer customer = repository.persist(new Customer(name));
        synchronizeWithDatabase();
        return customer;
    }
}
