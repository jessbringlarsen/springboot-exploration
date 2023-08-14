package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.resources.customer.mapper.CustomerDTOMapper;
import dk.bringlarsen.application.resources.customer.model.CustomerDTO;
import dk.bringlarsen.application.resources.customer.model.CustomerInputDTO;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.CreateCustomerUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "Customer")
public class CreateCustomerController {

    private final CreateCustomerUseCase usecase;
    private final CustomerDTOMapper mapper;

    @Autowired
    public CreateCustomerController(CreateCustomerUseCase usecase, CustomerDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Create customer")
    @PostMapping("/customers")
    public ResponseEntity<EntityModel<CustomerDTO>> create(@RequestBody CustomerInputDTO inputDTO) {
        try {
            Input input = new Input(inputDTO.getName());
            CustomerDTO customer = mapper.map(usecase.execute(input));
            EntityModel<CustomerDTO> customerResource = EntityModel.of(customer,
                linkTo(methodOn(GetCustomerController.class).findById(customer.getId())).withSelfRel());

            return ResponseEntity
                .created(new URI(customerResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                .body(customerResource);
        } catch (URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create Customer with name: " + inputDTO.getName());
        }
    }
}
