package com.example.Dingle.property.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties_images")
@Getter
@NoArgsConstructor
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "image_url")
    private String imageUrl;

    public PropertyImage(Property property, String imageUrl) {
        this.property = property;
        this.imageUrl = imageUrl;
    }
}
