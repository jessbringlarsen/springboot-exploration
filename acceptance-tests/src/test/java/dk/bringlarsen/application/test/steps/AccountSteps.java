package dk.bringlarsen.application.test.steps;

import dk.bringlarsen.application.test.api.AccountAPI;
import dk.bringlarsen.application.test.api.CustomerAPI;
import dk.bringlarsen.application.test.model.AccountDTO;
import dk.bringlarsen.application.test.model.CustomerDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountSteps {

    private final CustomerAPI customerAPI = new CustomerAPI();
    private final AccountAPI accountAPI = new AccountAPI();
    private CustomerDTO customerDTO;
    private AccountDTO accountDTO;

    @Given("an existing customer")
    public void anExistingCustomer() {
        customerDTO = customerAPI.createAndGetCustomer();
    }

    @When("an account is request to be created")
    public void anAccountIsRequestToBeCreated() {
        accountDTO = accountAPI.createAndGetForCustomer(customerDTO.id());

        assertEquals(customerDTO.id(), accountDTO.customerId());
    }

    @Then("expect an account to be created with a balance of {int}")
    public void expectAnAccountToBeCreated(int balance) {
        AccountDTO result = accountAPI.findByIdAndCustomerId(accountDTO.id(), customerDTO.id());

        assertEquals(new BigDecimal(balance), result.balance());
    }

    @Then("expect {int} number of accounts to be created")
    public void expectNoOfAccountsToBeCreated(int count) {
        AccountDTO[] result = accountAPI.findAllForCustomer(customerDTO.id());

        assertEquals(count, result.length);
    }
}
