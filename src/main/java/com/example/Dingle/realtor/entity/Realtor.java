package com.example.Dingle.realtor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Realtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "realtor_id", unique = true)
    private String realtorId;

    private String password;
    private String username;
    private String phone;
    private String email;

    private String officeName;
    private String officePhone;
    private String licenseNumber;

    private LocalDateTime createdAt;

}
