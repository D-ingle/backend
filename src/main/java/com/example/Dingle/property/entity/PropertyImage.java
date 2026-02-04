package com.example.Dingle.property.entity;

import com.example.Dingle.property.type.ImageType;
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

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "image_url")
    private String imageUrl;

    public PropertyImage(Property property, ImageType imageType, String imageUrl) {
        this.property = property;
        this.imageType = imageType;
        this.imageUrl = imageUrl;
    }
}
