package dk.bringlarsen.application.infrastructure.persistence.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.infrastructure.persistence.model.AccountEntity;
import dk.bringlarsen.application.infrastructure.persistence.model.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

    @Mapping(source = "account.id", target = "accountId")
    Transaction map(TransactionEntity from);

    List<Transaction> map(List<TransactionEntity> from);

    @Mapping(target = "id", source = "transaction.id")
    TransactionEntity map(Transaction transaction, AccountEntity account);
}
