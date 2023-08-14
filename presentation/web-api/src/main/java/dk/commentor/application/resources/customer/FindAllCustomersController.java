package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapper;
import dk.bringlarsen.application.resources.customer.model.CustomerDTO;
import dk.bringlarsen.application.usecase.customer.FindAllCustomersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Customer")
public class FindAllCustomersController {

    private final FindAllCustomersUseCase usecase;
    private final CustomerDTOMapper mapper;

    @Autowired
    public FindAllCustomersController(FindAllCustomersUseCase usecase, CustomerDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Find all Customers")
    @GetMapping(value = "/customers", produces = {"application/json"})
    public ResponseEntity<List<CustomerDTO>> findAll(@Parameter(description = "Page number to retrieve") @RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @Parameter(description = "Size of page to retrieve") @RequestParam(required = false, defaultValue = "50") Integer size,
                                                     @Parameter(description = "Field to sort by", example = "name,id") @RequestParam(required = false, defaultValue = "id") String sortFields,
                                                     @Parameter(description = "Ascending sort direction", example = "true") @RequestParam(required = false, defaultValue = "true") boolean sortDirectionAscending) {

        List<Customer> response = usecase.execute(page, size, sortDirectionAscending, sortFields);
        return ResponseEntity.ok(response.stream()
            .map(mapper::map)
            .collect(Collectors.toList()));

    }
}
