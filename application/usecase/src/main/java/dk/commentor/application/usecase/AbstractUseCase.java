package dk.bringlarsen.application.usecase;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public abstract class AbstractUseCase<T, R> {

    private final Validator validator;

    protected AbstractUseCase(Validator validator) {
        this.validator = validator;
    }

    private Set<ConstraintViolation<T>> isValid(T input) {
        return validator.validate(input);
    }

    public R execute(T input) {
        Set<ConstraintViolation<T>> violations = isValid(input);
        if (!violations.isEmpty()) {
            throw new UseCaseValidationException(violations);
        }
        return doExecute(input);
    }

    public abstract R doExecute(T input);
}
