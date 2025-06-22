package dk.bringlarsen.application.usecase.transaction;

import dk.bringlarsen.application.ApplicationConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.fail;

@Disabled("only for experimentation")
@SpringBootTest(classes = {ApplicationConfig.class, FindTransactionUseCaseProperties.class})
@Import({ValidationAutoConfiguration.class})
class FindTransactionUseCasePropertiesTest {

    @Autowired
    FindTransactionUseCaseProperties findTransactionUseCaseProperties;

    @Test
    @DisplayName("given missing configuration, expect startup failure")
    void failure() {
        fail("expected startup failure due to missing configuration");
    }
}
