package com.example.bookapi.controller;

import com.example.bookapi.dto.BookRequest;
import com.example.bookapi.dto.BookResponse;
import com.example.bookapi.dto.BookPageResponse;
import com.example.bookapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books.
 * Provides CRUD endpoints with validation and security.
 */
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Get all books (legacy endpoint for backward compatibility).
     * @return list of all books
     */
    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        // If no pagination parameters are provided, return all books (legacy behavior)
        if (page == 0 && size == 10 && "id".equals(sortBy) && "asc".equals(sortDir)) {
            List<BookResponse> books = bookService.findAll();
            return ResponseEntity.ok(books);
        }
        
        // Otherwise, return paginated results
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        BookPageResponse paginatedBooks = bookService.findAllPaginated(pageable);
        return ResponseEntity.ok(paginatedBooks);
    }

    /**
     * Get a book by its ID.
     * @param id the book ID
     * @return the book response
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    /**
     * Create a new book.
     * @param request the book request DTO
     * @return the created book response
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update an existing book.
     * @param id the book ID
     * @param request the book request DTO
     * @return the updated book response
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.update(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a book by its ID (admin only).
     * @param id the book ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 