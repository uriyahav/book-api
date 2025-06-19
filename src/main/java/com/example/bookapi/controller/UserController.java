package com.example.bookapi.controller;

import com.example.bookapi.model.User;
import com.example.bookapi.repository.UserRepository;
import com.example.bookapi.dto.UserOrderTotal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for user-related custom queries.
 * Provides endpoints for advanced user/order queries.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    /**
     * Get users with more than 3 orders.
     * @return list of users
     */
    @GetMapping("/more-than-3-orders")
    public List<User> getUsersWithMoreThan3Orders() {
        return userRepository.findUsersWithMoreThan3Orders();
    }

    /**
     * Get users with no orders.
     * @return list of users
     */
    @GetMapping("/no-orders")
    public List<User> getUsersWithNoOrders() {
        return userRepository.findUsersWithNoOrders();
    }

    /**
     * Get total order amount per user.
     * @return list of username/totalAmount pairs
     */
    @GetMapping("/total-order-amount")
    public List<UserOrderTotal> getTotalOrderAmountPerUser() {
        return userRepository.findTotalOrderAmountPerUser();
    }
} 