package dk.bringlarsen.application.test.api;

import dk.bringlarsen.application.provider.RestTemplateProvider;
import dk.bringlarsen.application.test.model.TransactionInputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class TransactionAPI {

    private final RestTemplate restTemplate = new RestTemplateProvider().provide();

    public int depositMoney(String accountId, TransactionInputDTO transaction) {
        try {
            return restTemplate
                .postForEntity("/accounts/{id}/deposit", transaction, String.class, accountId).getStatusCode().value();
        } catch (RestClientException e) {
            return HttpStatus.BAD_REQUEST.value();
        }
    }

    public int withdrawMoney(String accountId, TransactionInputDTO transaction) {
        try {
            return restTemplate
                .postForEntity("/accounts/{id}/withdraw", transaction, String.class, accountId).getStatusCode().value();
        } catch (RestClientException e) {
            return HttpStatus.BAD_REQUEST.value();
        }
    }
}
