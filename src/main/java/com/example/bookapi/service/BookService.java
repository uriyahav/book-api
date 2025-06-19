package com.example.bookapi.service;

import com.example.bookapi.dto.BookRequest;
import com.example.bookapi.dto.BookResponse;

import java.util.List;

/**
 * Service interface for book-related business logic.
 */
public interface BookService {
    /**
     * Get all books.
     * @return list of all books
     */
    List<BookResponse> findAll();

    /**
     * Get a book by its ID.
     * @param id the book ID
     * @return the book, or throws if not found
     */
    BookResponse findById(Long id);

    /**
     * Create a new book.
     * @param request the book request DTO
     * @return the created book
     */
    BookResponse create(BookRequest request);

    /**
     * Update an existing book.
     * @param id the book ID
     * @param request the book request DTO
     * @return the updated book
     */
    BookResponse update(Long id, BookRequest request);

    /**
     * Delete a book by its ID.
     * @param id the book ID
     */
    void delete(Long id);
} 