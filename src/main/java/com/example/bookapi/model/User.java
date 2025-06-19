package com.example.bookapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * JPA entity representing an application user.
 * Has a username, role, and a one-to-many relationship to orders.
 */
@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<Order> orders;

    public enum Role {
        USER, ADMIN
    }
} 