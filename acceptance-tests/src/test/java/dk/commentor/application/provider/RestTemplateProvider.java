package dk.bringlarsen.application.provider;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class RestTemplateProvider {

    private final RestTemplate restTemplate;

    public RestTemplateProvider() {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
    }

    public RestTemplate provide() {
        return restTemplate;
    }
}
