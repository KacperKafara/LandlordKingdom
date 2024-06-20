package pl.lodz.p.it.ssbd2024.util;

import jakarta.validation.Constraint;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = EndDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndDate {
}
