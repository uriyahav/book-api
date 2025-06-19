package com.example.bookapi.controller;

import com.example.bookapi.dto.BookRequest;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createAndGetBook() throws Exception {
        BookRequest request = BookRequest.builder()
                .title("Test Book")
                .author("Author")
                .publishedYear(2020)
                .build();
        // Create
        String response = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(response).get("id").asLong();
        // Get
        mockMvc.perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllBooks() throws Exception {
        bookRepository.save(Book.builder().title("A").author("B").publishedYear(2000).build());
        bookRepository.save(Book.builder().title("C").author("D").publishedYear(2010).build());
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateBook() throws Exception {
        Book book = bookRepository.save(Book.builder().title("Old").author("Old").publishedYear(1990).build());
        BookRequest request = BookRequest.builder().title("New").author("New").publishedYear(2021).build();
        mockMvc.perform(put("/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBookAsAdmin() throws Exception {
        Book book = bookRepository.save(Book.builder().title("ToDelete").author("A").publishedYear(2000).build());
        mockMvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void deleteBookAsUserForbidden() throws Exception {
        Book book = bookRepository.save(Book.builder().title("ToDelete").author("A").publishedYear(2000).build());
        mockMvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void unauthorizedAccessShouldFail() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void validationErrorShouldReturnBadRequest() throws Exception {
        BookRequest request = BookRequest.builder().title("").author("").publishedYear(1400).build();
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").exists())
                .andExpect(jsonPath("$.errors.author").exists())
                .andExpect(jsonPath("$.errors.publishedYear").exists());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void notFoundShouldReturn404() throws Exception {
        mockMvc.perform(get("/books/9999"))
                .andExpect(status().isNotFound());
    }
} 