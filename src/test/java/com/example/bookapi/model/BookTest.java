package com.example.bookapi.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validBookShouldHaveNoViolations() {
        Book book = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .publishedYear(2018)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankTitleShouldFailValidation() {
        Book book = Book.builder()
                .title("")
                .author("Joshua Bloch")
                .publishedYear(2018)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void blankAuthorShouldFailValidation() {
        Book book = Book.builder()
                .title("Effective Java")
                .author("")
                .publishedYear(2018)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("author")));
    }

    @Test
    void publishedYearTooEarlyShouldFailValidation() {
        Book book = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .publishedYear(1400)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("publishedYear")));
    }

    @Test
    void publishedYearInFutureShouldFailValidation() {
        Book book = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .publishedYear(3000)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("publishedYear")));
    }
} 