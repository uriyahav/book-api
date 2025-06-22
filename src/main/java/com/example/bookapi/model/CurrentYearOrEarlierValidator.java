package com.example.bookapi.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class CurrentYearOrEarlierValidator implements ConstraintValidator<CurrentYearOrEarlier, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true; // let @NotNull handle nulls if needed
        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
} 