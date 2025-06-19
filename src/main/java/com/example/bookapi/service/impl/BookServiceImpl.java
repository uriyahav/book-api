package com.example.bookapi.service.impl;

import com.example.bookapi.dto.*;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implementation of BookService for managing books.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
        return BookMapper.toResponse(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookResponse create(BookRequest request) {
        Book book = BookMapper.toEntity(request);
        Book saved = bookRepository.save(book);
        log.info("Created book: {} by {} (id={})", saved.getTitle(), saved.getAuthor(), saved.getId());
        return BookMapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedYear(request.getPublishedYear());
        Book updated = bookRepository.save(book);
        log.info("Updated book id={}", id);
        return BookMapper.toResponse(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent book id={}", id);
            throw new NoSuchElementException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Deleted book id={}", id);
    }
} 