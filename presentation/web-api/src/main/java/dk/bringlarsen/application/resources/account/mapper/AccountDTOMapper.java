package dk.bringlarsen.application.resources.account.mapper;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.model.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountDTOMapper {

    AccountDTO map(Account from);

    List<AccountDTO> map(List<Account> from);
}
