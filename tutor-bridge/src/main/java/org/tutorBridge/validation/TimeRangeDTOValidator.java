package org.tutorBridge.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.tutorBridge.dto.TimeRangeDTO;


public class TimeRangeDTOValidator implements ConstraintValidator<ValidTimeRangeDTO, TimeRangeDTO> {
    @Override
    public boolean isValid(TimeRangeDTO timeRangeDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (timeRangeDTO == null) {
            return true;
        }

        return timeRangeDTO.getStart().isBefore(timeRangeDTO.getEnd());
    }
}
