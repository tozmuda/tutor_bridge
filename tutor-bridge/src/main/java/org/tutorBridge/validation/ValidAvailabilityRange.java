package org.tutorBridge.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AvailabilityRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAvailabilityRange {
    String message() default "Invalid time range";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
