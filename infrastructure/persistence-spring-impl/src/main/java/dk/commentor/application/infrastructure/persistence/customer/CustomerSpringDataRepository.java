package dk.bringlarsen.application.infrastructure.persistence.customer;

import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CustomerSpringDataRepository extends CrudRepository<CustomerEntity, UUID>, PagingAndSortingRepository<CustomerEntity, UUID> {
}
