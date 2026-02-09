package com.example.Dingle.property.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties_descriptions")
@Getter
@NoArgsConstructor
public class PropertyDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private String safetyDescription;
    private String environmentDescription;
    private String noiseDescription;
    private String convenienceDescription;
    private String accessibilityDescription;

    public void updateEnvironmentDescription(String explanation) {
        this.environmentDescription = explanation;
    }

    public void updateConvenienceDescription(String description) {
        this.convenienceDescription = description;
    }

    public void updateAccessibilityDescription(String description) {
        this.accessibilityDescription = description;
    }

    public void updateNoiseDescription(String description) {
        this.noiseDescription = description;
    }

    public void updateSafetyDescription(String description) {
        this.safetyDescription = description;
    }
}
