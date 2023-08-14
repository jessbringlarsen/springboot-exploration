package dk.bringlarsen.application.infrastructure.persistence.transaction;

import dk.bringlarsen.application.infrastructure.persistence.model.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionSpringDataRepository extends CrudRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByAccountId(UUID id, Pageable page);
}
