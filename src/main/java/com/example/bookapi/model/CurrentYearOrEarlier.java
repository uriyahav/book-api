package com.example.bookapi.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrentYearOrEarlierValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentYearOrEarlier {
    String message() default "Published year must not be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 