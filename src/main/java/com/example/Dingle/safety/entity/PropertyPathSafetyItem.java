package com.example.Dingle.safety.entity;

import com.example.Dingle.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path_safety_item")
public class PropertyPathSafetyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private int crimeProneArea;
    private int cctv;
    private int safetyLight;

    public PropertyPathSafetyItem (
            Property property,
            int crimeProneArea,
            int cctv,
            int safetyLight
    ) {
        this.property = property;
        this.crimeProneArea = crimeProneArea;
        this.cctv = cctv;
        this.safetyLight = safetyLight;
    }
}
