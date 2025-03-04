package org.tutorBridge.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.tutorBridge.entities.Absence;

public class AbsenceRangeValidator implements ConstraintValidator<ValidAbsenceRange, Absence> {

    @Override
    public void initialize(ValidAbsenceRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Absence absence, ConstraintValidatorContext context) {
        if (absence == null) {
            return false;
        }
        if (absence.getStartDate() == null || absence.getEndDate() == null) {
            return false;
        }
        return absence.getStartDate().isBefore(absence.getEndDate());
    }
}
