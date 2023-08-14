package dk.bringlarsen.application.resources.transaction;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.mapper.TransactionDTOMapper;
import dk.bringlarsen.application.resources.transaction.model.TransactionDTO;
import dk.bringlarsen.application.resources.transaction.model.TransactionInputDTO;
import dk.bringlarsen.application.usecase.transaction.DepositMoneyUseCase;
import dk.bringlarsen.application.usecase.transaction.DepositMoneyUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Account")
public class DepositMoneyController {

    private final DepositMoneyUseCase usecase;
    private final TransactionDTOMapper mapper;

    @Autowired
    public DepositMoneyController(DepositMoneyUseCase usecase, TransactionDTOMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(summary = "Deposit money")
    @PostMapping(value = "/accounts/{id}/deposit")
    public ResponseEntity<TransactionDTO> deposit(@Parameter(description = "Id of account") @PathVariable("id") String id,
                                                  @RequestBody TransactionInputDTO input) {

        Transaction response = usecase.execute(new Input(id, input.text(), input.amount()));
        return ResponseEntity.ok(mapper.map(response));
    }
}
