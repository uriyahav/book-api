package com.example.bookapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersWithMoreThan3Orders() throws Exception {
        mockMvc.perform(get("/users/more-than-3-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].username", containsInAnyOrder("alice", "dave")));
    }

    @Test
    void getUsersWithNoOrders() throws Exception {
        mockMvc.perform(get("/users/no-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("carol")));
    }

    @Test
    void getTotalOrderAmountPerUser() throws Exception {
        mockMvc.perform(get("/users/total-order-amount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[?(@.username=='alice')].totalAmount", contains(100.0)))
                .andExpect(jsonPath("$[?(@.username=='bob')].totalAmount", contains(40.0)))
                .andExpect(jsonPath("$[?(@.username=='dave')].totalAmount", contains(75.0)));
    }
} 