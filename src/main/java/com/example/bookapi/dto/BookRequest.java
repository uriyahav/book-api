package com.example.bookapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    private String author;

    @Min(value = 1500, message = "Published year must be no earlier than 1500")
    @Max(value = 2024, message = "Published year must not be in the future")
    private Integer publishedYear;
} 