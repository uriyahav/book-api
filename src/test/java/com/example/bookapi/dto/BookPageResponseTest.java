package com.example.bookapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BookPageResponseTest {

    @Test
    void fromPage_shouldCreateCorrectBookPageResponse() {
        // Given
        BookResponse book1 = BookResponse.builder().id(1L).title("Book 1").author("Author 1").publishedYear(2000).build();
        BookResponse book2 = BookResponse.builder().id(2L).title("Book 2").author("Author 2").publishedYear(2001).build();
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<BookResponse> page = new PageImpl<>(Arrays.asList(book1, book2), pageRequest, 5);

        // When
        BookPageResponse response = BookPageResponse.fromPage(page);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(0, response.getPageNumber());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertTrue(response.hasNext());
        assertFalse(response.hasPrevious());
        assertTrue(response.isFirst());
        assertFalse(response.isLast());
        assertEquals("Book 1", response.getContent().get(0).getTitle());
        assertEquals("Book 2", response.getContent().get(1).getTitle());
    }

    @Test
    void fromPage_shouldHandleEmptyPage() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookResponse> emptyPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        // When
        BookPageResponse response = BookPageResponse.fromPage(emptyPage);

        // Then
        assertNotNull(response);
        assertEquals(0, response.getContent().size());
        assertEquals(0, response.getPageNumber());
        assertEquals(10, response.getPageSize());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertFalse(response.hasNext());
        assertFalse(response.hasPrevious());
        assertTrue(response.isFirst());
        assertTrue(response.isLast());
    }

    @Test
    void fromPage_shouldHandleLastPage() {
        // Given
        BookResponse book = BookResponse.builder().id(1L).title("Last Book").author("Author").publishedYear(2000).build();
        PageRequest pageRequest = PageRequest.of(2, 2); // Last page
        Page<BookResponse> lastPage = new PageImpl<>(Collections.singletonList(book), pageRequest, 5);

        // When
        BookPageResponse response = BookPageResponse.fromPage(lastPage);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(2, response.getPageNumber());
        assertEquals(2, response.getPageSize());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertFalse(response.hasNext());
        assertTrue(response.hasPrevious());
        assertFalse(response.isFirst());
        assertTrue(response.isLast());
    }

    @Test
    void builder_shouldCreateBookPageResponse() {
        // Given
        BookResponse book = BookResponse.builder().id(1L).title("Test Book").author("Test Author").publishedYear(2000).build();

        // When
        BookPageResponse response = BookPageResponse.builder()
                .content(Collections.singletonList(book))
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .first(true)
                .last(true)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPageNumber());
        assertEquals(10, response.getPageSize());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertFalse(response.hasNext());
        assertFalse(response.hasPrevious());
        assertTrue(response.isFirst());
        assertTrue(response.isLast());
    }
} 