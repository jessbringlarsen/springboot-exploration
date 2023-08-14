package dk.bringlarsen.application.infrastructure.applicationinformation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ApplicationInformationServiceSpringImpl.class, BuildProperties.class},
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles(profiles = {"test"})
class ApplicationInformationServiceSpringImplProfileTest {

    @Autowired
    ApplicationInformationServiceSpringImpl service;

    @Test
    void givenTestProfileIsActiveExpectTestEnvironment() {
        assertEquals("test", service.getEnvironment());
    }
}
