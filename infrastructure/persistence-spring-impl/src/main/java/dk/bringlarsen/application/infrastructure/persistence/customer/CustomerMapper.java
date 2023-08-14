package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerMapper {

    CustomerEntity map(Customer customer) {
        UUID id = customer.getId() != null ? UUID.fromString(customer.getId()) : null;
        return new CustomerEntity()
            .setId(id)
            .setName(customer.getName());
    }

    Customer map(CustomerEntity customerEntity) {
        String id = customerEntity.getId() != null ? customerEntity.getId().toString() : null;
        return new Customer(id, customerEntity.getName());
    }
}
