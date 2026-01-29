package com.example.Dingle.user.entity;

import com.example.Dingle.user.type.PreferredType;
import com.example.Dingle.user.type.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private PreferredType preferredType;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDateTime createdAt;
    private LocalDateTime onboardedAt;
}
