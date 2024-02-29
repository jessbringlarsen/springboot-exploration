package dk.bringlarsen.application.test.steps;

import dk.bringlarsen.application.test.api.CustomerAPI;
import dk.bringlarsen.application.test.model.CustomerDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerSteps {

    private final CustomerAPI customerAPI = new CustomerAPI();
    private String name;
    private URI newCustomerLink;

    @Given("a non existing customer with name {string}")
    public void aNonExistingCustomer(String name) {
        this.name = name;
    }

    @When("the customer request to be created")
    public void theCustomerRequestToBeCreated() {
        newCustomerLink = customerAPI.createWithName(name);

        assertNotNull(newCustomerLink, "Expected a link to the created customer");
    }

    @Then("expect a customer with name {string} to be created")
    public void expectCustomerToBeCreated(String name) {
        Optional<CustomerDTO> customer = customerAPI.lookup(newCustomerLink);

        assertTrue(customer.isPresent());
        assertEquals(name, customer.get().name());
    }

    @And("the customer is deleted")
    public void theCustomerIsDeleted() {
        customerAPI.delete(newCustomerLink);
    }

    @Then("expect that the customer is not found")
    public void expectCustomerToBeDeleted() {
        Optional<CustomerDTO> customer = customerAPI.lookup(newCustomerLink);

        assertFalse(customer.isPresent());
    }
}
