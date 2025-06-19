package com.example.bookapi.repository;

import com.example.bookapi.model.User;
import com.example.bookapi.model.User.Role;
import com.example.bookapi.dto.UserOrderTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repository for User entity with custom queries for advanced user/order scenarios.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find users with more than 3 orders.
     * @return list of users
     */
    @Query("SELECT u FROM User u WHERE SIZE(u.orders) > 3")
    List<User> findUsersWithMoreThan3Orders();

    /**
     * Find users who have not placed any orders.
     * @return list of users
     */
    @Query("SELECT u FROM User u WHERE u.orders IS EMPTY")
    List<User> findUsersWithNoOrders();

    /**
     * Find total order amount per user.
     * @return list of username/totalAmount pairs
     */
    @Query("SELECT new com.example.bookapi.dto.UserOrderTotal(u.username, SUM(o.amount)) FROM User u JOIN u.orders o GROUP BY u.username")
    List<UserOrderTotal> findTotalOrderAmountPerUser();

    List<User> findByRole(Role role);
} 