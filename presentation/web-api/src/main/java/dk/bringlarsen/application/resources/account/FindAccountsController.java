package dk.bringlarsen.application.resources.account;

import dk.bringlarsen.application.domain.model.Account;
import dk.bringlarsen.application.resources.account.mapper.AccountDTOMapper;
import dk.bringlarsen.application.resources.account.model.AccountDTO;
import dk.bringlarsen.application.usecase.account.FindAccountsUseCase;
import dk.bringlarsen.application.usecase.account.FindAccountsUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Customer")
public class FindAccountsController {

    private final FindAccountsUseCase usecase;
    private final AccountDTOMapper mapper;

    @Autowired
    public FindAccountsController(FindAccountsUseCase usecase, AccountDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Find accounts for a customer")
    @GetMapping(value = "/customers/{id}/accounts")
    public ResponseEntity<List<AccountDTO>> findByCustomerId(@Parameter(description = "Page number to retrieve") @RequestParam(required = false, defaultValue = "0") Integer page,
                                                             @Parameter(description = "Size of page to retrieve") @RequestParam(required = false, defaultValue = "1000") Integer size,
                                                             @PathVariable String id) {

        List<Account> response = usecase.execute(new Input(id, size, page));
        return ResponseEntity.ok(mapper.map(response));
    }


}
