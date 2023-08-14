package dk.bringlarsen.application.resources.customer.mapper;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerDTOMapper {

    CustomerDTO map(Customer customer);
}
