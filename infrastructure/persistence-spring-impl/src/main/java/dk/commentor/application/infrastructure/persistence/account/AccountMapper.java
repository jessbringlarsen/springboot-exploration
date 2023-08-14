package dk.bringlarsen.application.infrastructure.persistence.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.infrastructure.persistence.model.AccountEntity;
import dk.bringlarsen.application.infrastructure.persistence.model.CustomerEntity;
import dk.bringlarsen.application.infrastructure.persistence.transaction.TransactionMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    uses = {TransactionMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy= ReportingPolicy.ERROR)
public interface AccountMapper {

    @Mapping(target = "customerId", source = "customer.id")
    Account map(AccountEntity accountEntity);

    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "name", source = "account.name")
    @Mapping(target = "transactions", ignore = true)
    AccountEntity map(Account account, CustomerEntity customer);
}
