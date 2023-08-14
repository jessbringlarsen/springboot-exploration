package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapper;
import dk.bringlarsen.application.resources.customer.model.CustomerDTO;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.GetCustomerUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "Customer")
public class GetCustomerController {

    private final GetCustomerUseCase usecase;
    private final CustomerDTOMapper mapper;

    @Autowired
    public GetCustomerController(GetCustomerUseCase usecase, CustomerDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @GetMapping("/customers/{id}")
    @Operation(summary = "Get a Customer with a specific id")
    public ResponseEntity<EntityModel<CustomerDTO>> findById(@PathVariable String id) {
        return usecase.execute(new Input(id))
            .map(mapper::map)
            .map(customer -> EntityModel.of(customer,
                linkTo(methodOn(GetCustomerController.class).findById(customer.getId())).withSelfRel(),
                linkTo(methodOn(FindAllCustomersController.class)
                    .findAll(0, 100, "name", true)).withRel("customers")))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
