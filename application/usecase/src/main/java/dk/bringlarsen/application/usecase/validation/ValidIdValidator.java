package dk.bringlarsen.application.usecase.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidIdValidator implements ConstraintValidator<ValidId, String> {

    private static final Pattern UUID_REGEX =
        Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Override
    public void initialize(ValidId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        if (id == null) {
            return false;
        }
        return UUID_REGEX.matcher(id).matches();
    }
}
