package org.tutorBridge.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeRangeDTOValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeRangeDTO {
    String message() default "Invalid time range";

    Class<?>[] groups
            () default {};

    Class<? extends Payload>[] payload() default {};
}
