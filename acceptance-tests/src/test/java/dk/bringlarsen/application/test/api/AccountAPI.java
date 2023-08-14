package dk.bringlarsen.application.test.api;

import dk.bringlarsen.application.provider.RestTemplateProvider;
import dk.bringlarsen.application.test.model.AccountDTO;
import dk.bringlarsen.application.test.model.AccountInputDTO;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class AccountAPI {

    private final RestTemplate restTemplate = new RestTemplateProvider().provide();

    public AccountDTO createAndGetForCustomer(String customerId, String accountName) {
        return restTemplate.postForObject("/customers/" + customerId + "/accounts",
            new AccountInputDTO(customerId, accountName),
            AccountDTO.class);
    }

    public AccountDTO createAndGetForCustomer(String customerId) {
        return createAndGetForCustomer(customerId, UUID.randomUUID().toString());
    }

    public AccountDTO findByIdAndCustomerId(String accountId, String customerId) {
        return restTemplate.getForObject("/customers/{customerId}/accounts/{accountId}",
            AccountDTO.class, customerId, accountId);
    }

    public AccountDTO[] findAllForCustomer(String customerId) {
        return restTemplate.getForObject("/customers/{customerId}/accounts",
            AccountDTO[].class, customerId);
    }

    public AccountDTO getById(String customerId, String id) {
        return restTemplate.getForObject("/customers/{customerId}/accounts/{id}",
            AccountDTO.class, customerId, id);
    }
}
