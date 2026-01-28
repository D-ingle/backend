package com.example.Dingle.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtors")
@Getter
@Setter
public class RealtorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realtorId;
    private String password;
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String phone;

    private String officeName;
    private String officePhone;
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDateTime createdAt;
}
