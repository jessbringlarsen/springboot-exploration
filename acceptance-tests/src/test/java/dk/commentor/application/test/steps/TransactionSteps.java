package dk.bringlarsen.application.test.steps;

import dk.bringlarsen.application.test.api.AccountAPI;
import dk.bringlarsen.application.test.api.CustomerAPI;
import dk.bringlarsen.application.test.api.TransactionAPI;
import dk.bringlarsen.application.test.model.AccountDTO;
import dk.bringlarsen.application.test.model.CustomerDTO;
import dk.bringlarsen.application.test.model.TransactionInputDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransactionSteps {

    private final CustomerAPI customerAPI = new CustomerAPI();
    private final AccountAPI accountAPI = new AccountAPI();
    private final TransactionAPI transactionAPI = new TransactionAPI();

    private AccountDTO account;
    private int httpResponseCode;

    @Given("an existing account named {string} with balance of {string} dollars")
    public void anExistingAccountNamedSavings(String accountName, String amount) {
        CustomerDTO customer = customerAPI.createAndGetCustomer();
        account = accountAPI.createAndGetForCustomer(customer.id(), accountName);
        transactionAPI.depositMoney(account.id(),
            new TransactionInputDTO("text", new BigDecimal(amount)));
    }

    @When("{string} dollars are deposited into the account")
    public void dollarsAreDepositedIntoTheAccount(String amount) {
        TransactionInputDTO transaction = new TransactionInputDTO("Salary", new BigDecimal(amount));
        httpResponseCode = transactionAPI.depositMoney(account.id(), transaction);
    }


    @When("{string} dollars are withdrawn from the account")
    public void dollarsAreWithdrawnFromTheAccount(String amount) {
        TransactionInputDTO transaction = new TransactionInputDTO("Coffee at a cafe", new BigDecimal(amount));
        httpResponseCode = transactionAPI.withdrawMoney(account.id(), transaction);
    }

    @Then("the account must have a balance of {string}")
    public void theAccountMustHaveABalanceOf(String amount) {
        AccountDTO account = accountAPI.getById(this.account.customerId(), this.account.id());

        assertEquals(new BigDecimal(amount), account.balance());
    }

    @Then("the operation is refused")
    public void theOperationIsRefused() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), httpResponseCode);
    }
}
