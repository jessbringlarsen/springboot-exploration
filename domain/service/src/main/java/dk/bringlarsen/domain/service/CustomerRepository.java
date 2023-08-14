package dk.bringlarsen.domain.service;

import dk.bringlarsen.application.domain.model.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the Customer entity.
 */
public interface CustomerRepository {

    /**
     * Respsible for persisting objects of the type {@link Customer}
     *
     * @param customer - object to persist.
     * @return the {@link Customer} just created
     * @throws PersistException if unable to persist.
     */
    Customer persist(Customer customer);

    /**
     * Responsible for finding a {@link Customer} by id
     *
     * @param id - of customer
     * @return the {@link Customer} with the id if one exists.
     */
    Optional<Customer> findById(String id);


    /**
     * Pageable service for finding all customers.
     *
     * @param page - {@link Pageable} object defining page to retrieve.
     * @return a list of customers.
     */
    List<Customer> findAll(Pageable page);

    /**
     * Delete a customer with a given id.
     *
     * @param id - of customer
     */
    void delete(String id);
}
