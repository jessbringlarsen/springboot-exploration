package dk.bringlarsen.application.test.api;

import dk.bringlarsen.application.provider.RestTemplateProvider;
import dk.bringlarsen.application.test.model.CustomerDTO;
import dk.bringlarsen.application.test.model.CustomerInputDTO;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static java.util.UUID.randomUUID;

public class CustomerAPI {

    private final RestTemplate restTemplate = new RestTemplateProvider().provide();

    public URI createWithName(String name) {
        return restTemplate.postForLocation("/customers", new CustomerInputDTO().setCustomerName(name));
    }

    public Optional<CustomerDTO> lookup(URI link) {
        try {
            return Optional.ofNullable(restTemplate.getForEntity(link, CustomerDTO.class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public CustomerDTO createAndGetCustomer() {
        URI customerLink = createWithName(randomUUID().toString());
        Optional<CustomerDTO> result = lookup(customerLink);
        if (result.isEmpty()) {
            Assertions.fail("Unable to retrieve created customer!");
        }
        return result.get();
    }

    public void delete(URI link) {
        restTemplate.delete(link);
    }
}
