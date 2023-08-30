package dk.bringlarsen.application.resources.applicationinformation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationInformationIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void expectResourceToRespond() {
        ResponseEntity<ApplicationInformation> response = restTemplate.getForEntity("/apiVersion", ApplicationInformation.class);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }
}
