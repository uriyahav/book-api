package com.example.bookapi.service;

import com.example.bookapi.dto.*;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnListOfBookResponses() {
        List<Book> books = List.of(
                Book.builder().id(1L).title("A").author("B").publishedYear(2000).build(),
                Book.builder().id(2L).title("C").author("D").publishedYear(2010).build()
        );
        when(bookRepository.findAll()).thenReturn(books);
        List<BookResponse> responses = bookService.findAll();
        assertEquals(2, responses.size());
        assertEquals("A", responses.get(0).getTitle());
        assertEquals("C", responses.get(1).getTitle());
    }

    @Test
    void findAllPaginated_shouldReturnPaginatedBookResponses() {
        // Given
        List<Book> books = List.of(
                Book.builder().id(1L).title("A").author("B").publishedYear(2000).build(),
                Book.builder().id(2L).title("C").author("D").publishedYear(2010).build()
        );
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(0, 2), 5);
        when(bookRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(bookPage);

        // When
        BookPageResponse response = bookService.findAllPaginated(PageRequest.of(0, 2));

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
    }

    @Test
    void findAllPaginated_shouldHandleEmptyPage() {
        // Given
        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        when(bookRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(emptyPage);

        // When
        BookPageResponse response = bookService.findAllPaginated(PageRequest.of(0, 10));

        // Then
        assertNotNull(response);
        assertEquals(0, response.getContent().size());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertFalse(response.hasNext());
        assertFalse(response.hasPrevious());
        assertTrue(response.isFirst());
        assertTrue(response.isLast());
    }

    @Test
    void findById_shouldReturnBookResponse() {
        Book book = Book.builder().id(1L).title("A").author("B").publishedYear(2000).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookResponse response = bookService.findById(1L);
        assertEquals("A", response.getTitle());
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> bookService.findById(1L));
    }

    @Test
    void create_shouldSaveAndReturnBookResponse() {
        BookRequest request = BookRequest.builder().title("A").author("B").publishedYear(2000).build();
        Book book = Book.builder().title("A").author("B").publishedYear(2000).build();
        Book saved = Book.builder().id(1L).title("A").author("B").publishedYear(2000).build();
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(saved);
        BookResponse response = bookService.create(request);
        assertEquals(1L, response.getId());
        assertEquals("A", response.getTitle());
    }

    @Test
    void update_shouldUpdateAndReturnBookResponse() {
        Book existing = Book.builder().id(1L).title("Old").author("Old").publishedYear(1990).build();
        BookRequest request = BookRequest.builder().title("New").author("New").publishedYear(2020).build();
        Book updated = Book.builder().id(1L).title("New").author("New").publishedYear(2020).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(existing)).thenReturn(updated);
        BookResponse response = bookService.update(1L, request);
        assertEquals("New", response.getTitle());
        assertEquals(2020, response.getPublishedYear());
    }

    @Test
    void update_shouldThrowIfNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        BookRequest request = BookRequest.builder().title("A").author("B").publishedYear(2000).build();
        assertThrows(NoSuchElementException.class, () -> bookService.update(1L, request));
    }

    @Test
    void delete_shouldRemoveBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);
        assertDoesNotThrow(() -> bookService.delete(1L));
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> bookService.delete(1L));
    }
} 