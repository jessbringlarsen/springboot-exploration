package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapper;
import dk.bringlarsen.application.resources.account.model.AccountDTO;
import dk.bringlarsen.application.usecase.account.GetAccountUseCase;
import dk.bringlarsen.application.usecase.account.GetAccountUseCase.Input;
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
public class GetAccountController {

    private final GetAccountUseCase usecase;
    private final AccountDTOMapper mapper;

    @Autowired
    public GetAccountController(GetAccountUseCase usecase, AccountDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Get a specific account by id")
    @GetMapping("/customers/{customerId}/accounts/{accountId}")
    public ResponseEntity<EntityModel<AccountDTO>> findById(@PathVariable("customerId") String customerId,
                                                            @PathVariable("accountId") String accountId) {
        return usecase.execute(new Input(customerId, accountId))
            .map(mapper::map)
            .map(account -> EntityModel.of(account,
                linkTo(methodOn(GetAccountController.class).findById(customerId, accountId)).withSelfRel()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
