package org.tutorBridge.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.tutorBridge.entities.Availability;

public class AvailabilityRangeValidator implements ConstraintValidator<ValidAvailabilityRange, Availability> {
    @Override
    public boolean isValid(Availability availability, ConstraintValidatorContext constraintValidatorContext) {
        if (availability == null) {
            return false;
        }
        return availability.getStartDateTime().isBefore(availability.getEndDateTime());
    }
}
