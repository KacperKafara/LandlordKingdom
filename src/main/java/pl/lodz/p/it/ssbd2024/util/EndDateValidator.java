package pl.lodz.p.it.ssbd2024.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
