package dk.bringlarsen.application.infrastructure.persistence.account;

import dk.bringlarsen.application.infrastructure.persistence.model.AccountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountSpringDataRepository extends CrudRepository<AccountEntity, UUID> {

    List<AccountEntity> findByCustomerId(UUID id, Pageable pageable);

    Optional<AccountEntity> findByIdAndCustomerId(UUID id, UUID customerId);
}
