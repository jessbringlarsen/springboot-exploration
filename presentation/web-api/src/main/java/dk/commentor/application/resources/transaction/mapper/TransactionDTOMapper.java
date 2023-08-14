package dk.bringlarsen.application.resources.transaction.mapper;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.model.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionDTOMapper {

    TransactionDTO map(Transaction transaction);

    List<TransactionDTO> map(List<Transaction> transaction);
}
