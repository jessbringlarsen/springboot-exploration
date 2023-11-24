package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerSpringJdbcRepository repository;

    @Autowired
    public CustomerRepositoryImpl(CustomerSpringJdbcRepository repository) {
        this.repository = repository;
    }


    @Override
    public Customer persist(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll(Pageable page) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
