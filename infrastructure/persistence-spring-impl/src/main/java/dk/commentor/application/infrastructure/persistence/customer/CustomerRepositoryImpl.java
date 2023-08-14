package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.Pageable;
import dk.bringlarsen.domain.service.PersistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;


@Repository
class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerSpringDataRepository customerRepository;
    private final CustomerMapper mapper;

    @Autowired
    CustomerRepositoryImpl(CustomerSpringDataRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public Customer persist(Customer customer) {
        try {
            CustomerEntity customerEntity = customerRepository.save(mapper.map(customer));
            return mapper.map(customerEntity);
        } catch (DataAccessException e) {
            throw new PersistException("Unable to persist customer", e);
        }
    }

    @Override
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(UUID.fromString(id)).stream()
            .map(mapper::map)
            .findFirst();
    }

    public List<Customer> findAll(Pageable page) {
        PageRequest pageing = PageRequest.of(page.getPage(), page.getSize(),
            page.sortAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
            page.getSortFields());

        return StreamSupport.stream(customerRepository.findAll(pageing).spliterator(), true)
            .map(mapper::map)
            .toList();
    }

    /**
     * ***********************************
     * #HandsOn - Implement DeleteCustomer
     * ***********************************
     */
    @Override
    public void delete(String id) {
        customerRepository.deleteById(UUID.fromString(id));
    }
}
