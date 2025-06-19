package com.example.bookapi.dto;

import com.example.bookapi.model.Book;

public class BookMapper {
    public static Book toEntity(BookRequest request) {
        if (request == null) return null;
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publishedYear(request.getPublishedYear())
                .build();
    }

    public static BookResponse toResponse(Book book) {
        if (book == null) return null;
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .build();
    }

    public static void updateEntity(Book book, BookRequest request) {
        if (book != null && request != null) {
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPublishedYear(request.getPublishedYear());
        }
    }
} 