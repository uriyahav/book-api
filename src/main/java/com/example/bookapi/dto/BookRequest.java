package com.example.bookapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import com.example.bookapi.model.CurrentYearOrEarlier;

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
    @CurrentYearOrEarlier
    private Integer publishedYear;
} 