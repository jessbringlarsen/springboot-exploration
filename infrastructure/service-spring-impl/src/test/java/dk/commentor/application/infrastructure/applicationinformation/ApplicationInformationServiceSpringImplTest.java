package dk.bringlarsen.application.infrastructure.applicationinformation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ApplicationInformationServiceSpringImpl.class},
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationInformationServiceSpringImplTest {

    @Autowired
    ApplicationInformationServiceSpringImpl service;
    @MockBean
    BuildProperties buildProperties;

    @BeforeEach
    void setUp() {
        when(buildProperties.getVersion()).thenReturn("1.0.0");
    }

    @Test
    void givenDefaultProfileIsActiveExpectProductionEnvironment() {
        assertEquals("production", service.getEnvironment());
    }

    @Test
    void expectJavaAsTechnology() {
        assertEquals("Java", service.getTechnology());
    }

    @Test
    void expectVersion() {
        assertEquals("1.0.0", service.getVersion());
    }
}
