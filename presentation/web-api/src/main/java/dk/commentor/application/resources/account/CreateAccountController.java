package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapper;
import dk.bringlarsen.application.resources.account.model.AccountDTO;
import dk.bringlarsen.application.resources.account.model.AccountInputDTO;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase;
import dk.bringlarsen.application.usecase.account.CreateAccountUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "Customer")
public class CreateAccountController {

    private final Logger logger = LoggerFactory.getLogger(CreateAccountController.class);
    private final CreateAccountUseCase usecase;
    private final AccountDTOMapper mapper;

    @Autowired
    public CreateAccountController(CreateAccountUseCase usecase, AccountDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Create account for a customer")
    @PostMapping(value = "/customers/{id}/accounts")
    public ResponseEntity<EntityModel<AccountDTO>> create(@Parameter(description = "Id of customer") @PathVariable("id") String id,
                                                          @RequestBody AccountInputDTO input) {

        try {
            AccountDTO account = mapper.map(usecase.execute(new Input(id, input.name())));
            EntityModel<AccountDTO> resource = EntityModel.of(account,
                linkTo(methodOn(GetAccountController.class)
                    .findById(account.id().toString(), account.customerId().toString())).withSelfRel());
            return ResponseEntity
                .created(new URI(resource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                .body(resource);
        } catch (URISyntaxException e) {
            logger.error("Unable to create account", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
