package dk.bringlarsen.application.usecase;

/**
 * Represent an unexpected event occurred during execution of an usecase.
 */
public class UseCaseException extends RuntimeException {

    public UseCaseException(String message) {
        super(message);
    }

    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
