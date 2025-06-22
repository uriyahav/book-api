package com.example.bookapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * JPA entity representing a book in the system.
 * Includes title, author, and published year with validation.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    private String author;

    @Min(value = 1500, message = "Published year must be no earlier than 1500")
    @CurrentYearOrEarlier
    private Integer publishedYear;
} 