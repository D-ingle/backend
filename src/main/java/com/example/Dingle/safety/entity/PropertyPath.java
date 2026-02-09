package com.example.Dingle.safety.entity;

import com.example.Dingle.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path")
public class PropertyPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(columnDefinition = "geometry(LineString,4326)", nullable = false)
    private String pathWkt;

    public PropertyPath(Property property, String pathWkt) {
        this.property = property;
        this.pathWkt = pathWkt;
    }
}
