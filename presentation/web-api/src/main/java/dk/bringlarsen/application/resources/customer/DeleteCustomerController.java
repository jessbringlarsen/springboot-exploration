package dk.bringlarsen.application.resources.customer;

import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase;
import dk.bringlarsen.application.usecase.customer.DeleteCustomerUseCase.Input;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ***********************************
 * #HandsOn - Implement DeleteCustomer
 * ***********************************
 */
@RestController
@Tag(name = "Customer")
public class DeleteCustomerController {

    private final DeleteCustomerUseCase usecase;

    @Autowired
    public DeleteCustomerController(DeleteCustomerUseCase usecase) {
        this.usecase = usecase;
    }

    @DeleteMapping("/customers/{id}")
    @Operation(summary = "Delete a Customer with a specific id")
    public ResponseEntity<String> delete(@PathVariable String id) {

        usecase.execute(new Input(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
