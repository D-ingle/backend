package com.example.Dingle.user.entity;

import com.example.Dingle.property.entity.Property;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private SavedProperty(User user, Property property) {
        this.user = user;
        this.property = property;
        this.createdAt = LocalDateTime.now();
    }

    public static SavedProperty create(User user, Property property) {
        return new SavedProperty(user, property);
    }
}
