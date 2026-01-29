package com.example.Dingle.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "saved_properties")
public class SavedProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private SavedProperty(String userId, Long propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.createdAt = LocalDateTime.now();
    }

    public static SavedProperty create(String userId, Long propertyId) {
        return new SavedProperty(userId, propertyId);
    }
}
