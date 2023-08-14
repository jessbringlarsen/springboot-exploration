package dk.bringlarsen.application.usecase.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIdValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface ValidId {

    String message() default "not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
