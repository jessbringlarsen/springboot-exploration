package dk.bringlarsen.application.usecase.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidIdValidatorTest {

    ValidIdValidator validator = new ValidIdValidator();

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyInput(String uuid) {
        assertFalse(isValid(uuid));
    }

    @Test
    void testInvalidInput() {
        assertFalse(isValid("INVALID"));
    }

    @Test
    void testValidInput() {
        assertTrue(isValid(UUID.randomUUID().toString()));
    }

    private boolean isValid(String uuid) {
        return validator.isValid(uuid, null);
    }
}
