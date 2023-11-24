package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CustomerSpringJdbcRepository extends CrudRepository<Customer, Integer> {
}
