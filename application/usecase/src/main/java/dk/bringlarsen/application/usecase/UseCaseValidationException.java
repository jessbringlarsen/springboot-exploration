package dk.bringlarsen.application.usecase;

import jakarta.validation.ConstraintViolation;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represent a usecase input validation failure.
 */
public class UseCaseValidationException extends RuntimeException {

    public UseCaseValidationException(Collection<? extends ConstraintViolation<?>> constraintViolations) {
        this(constraintViolations.stream()
            .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
            .collect(Collectors.joining(", ")));
    }

    public UseCaseValidationException(String message) {
        super(message);
    }
}
