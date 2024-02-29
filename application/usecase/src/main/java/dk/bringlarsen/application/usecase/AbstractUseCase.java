package dk.bringlarsen.application.usecase;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class AbstractUseCase<T, R> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Validator validator;
    private final PerformanceMonitor<R> performanceMonitor;

    protected AbstractUseCase(Validator validator) {
        this.validator = validator;
        this.performanceMonitor = new PerformanceMonitor<>();
    }

    private Set<ConstraintViolation<T>> isValid(T input) {
        return validator.validate(input);
    }

    public R execute(T input) {
        Set<ConstraintViolation<T>> violations = isValid(input);
        if (!violations.isEmpty()) {
            throw new UseCaseValidationException(violations);
        }
        return performanceMonitor.intercept(logger, () -> doExecute(input));
    }

    public abstract R doExecute(T input);
}
