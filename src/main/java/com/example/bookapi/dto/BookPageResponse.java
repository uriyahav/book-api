package com.example.bookapi.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO for paginated book responses.
 * Includes content and pagination metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPageResponse {
    private List<BookResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean first;
    private boolean last;

    /**
     * Create BookPageResponse from Spring Data Page.
     * @param page the Spring Data Page
     * @return BookPageResponse
     */
    public static BookPageResponse fromPage(Page<BookResponse> page) {
        return BookPageResponse.builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    // Explicit getters to ensure they are available
    public boolean hasNext() {
        return hasNext;
    }

    public boolean hasPrevious() {
        return hasPrevious;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }
} 