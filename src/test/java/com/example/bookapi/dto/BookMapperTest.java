package com.example.bookapi.dto;

import com.example.bookapi.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    @Test
    void toEntity_shouldMapRequestToEntity() {
        BookRequest request = BookRequest.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .publishedYear(2008)
                .build();
        Book book = BookMapper.toEntity(request);
        assertNotNull(book);
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals(2008, book.getPublishedYear());
    }

    @Test
    void toResponse_shouldMapEntityToResponse() {
        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .publishedYear(2008)
                .build();
        BookResponse response = BookMapper.toResponse(book);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Clean Code", response.getTitle());
        assertEquals("Robert C. Martin", response.getAuthor());
        assertEquals(2008, response.getPublishedYear());
    }

    @Test
    void updateEntity_shouldUpdateEntityFields() {
        Book book = Book.builder()
                .id(1L)
                .title("Old Title")
                .author("Old Author")
                .publishedYear(1990)
                .build();
        BookRequest request = BookRequest.builder()
                .title("New Title")
                .author("New Author")
                .publishedYear(2020)
                .build();
        BookMapper.updateEntity(book, request);
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals(2020, book.getPublishedYear());
    }

    @Test
    void nullSafety() {
        assertNull(BookMapper.toEntity(null));
        assertNull(BookMapper.toResponse(null));
        Book book = Book.builder().build();
        BookMapper.updateEntity(book, null); // Should not throw
        assertNotNull(book);
    }
} 