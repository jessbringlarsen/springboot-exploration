package dk.bringlarsen.application.usecase;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ConstraintValidationTest {

    protected static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    protected void assertViolations(UseCaseValidationException exception, String... messages) {
        assertThat(exception.getMessage()).contains(messages);
    }
}
