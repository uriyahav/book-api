package com.example.bookapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for username and total order amount per user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderTotal {
    private String username;
    private Double totalAmount;
} 