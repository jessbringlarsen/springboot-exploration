package dk.bringlarsen.application.resources.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.mapper.TransactionDTOMapper;
import dk.bringlarsen.application.resources.transaction.model.TransactionDTO;
import dk.bringlarsen.application.usecase.transaction.FindTransactionUseCase;
import dk.bringlarsen.application.usecase.transaction.FindTransactionUseCase.Input;
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
@Tag(name = "Account")
public class FindTransactionController {

    private final FindTransactionUseCase usecase;
    private final TransactionDTOMapper mapper;

    @Autowired
    public FindTransactionController(FindTransactionUseCase usecase, TransactionDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Find transactions for account")
    @GetMapping(value = "/accounts/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> findByAccountId(@Parameter(description = "Page number to retrieve") @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                @Parameter(description = "Size of page to retrieve") @RequestParam(required = false, defaultValue = "50") Integer size,
                                                                @Parameter(description = "Id of account") @PathVariable("id") String id) {
        List<Transaction> result = usecase.execute(new Input(id, page, size));
        return ResponseEntity.ok(mapper.map(result));
    }
}
