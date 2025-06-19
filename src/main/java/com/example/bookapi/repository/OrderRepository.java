package com.example.bookapi.repository;

import com.example.bookapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Order entity. Provides CRUD and custom queries for orders.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
} 