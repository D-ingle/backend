package com.example.Dingle.property.entity;

import com.example.Dingle.property.dto.PropertyScoreDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties_scores")
@Getter
@NoArgsConstructor
public class PropertyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int safetyScore;
    private int environmentScore;
    private int noiseScore;
    private int convenienceScore;
    private int accessibilityScore;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "property_id", insertable = false, updatable = false)
    private Long propertyId;

    public static PropertyScoreDTO fromEntity(PropertyScore entity) {
        return PropertyScoreDTO.builder()
                .propertyId(entity.getProperty().getId())
                .noiseScore(entity.getNoiseScore())
                .safetyScore(entity.getSafetyScore())
                .convenienceScore(entity.getConvenienceScore())
                .accessibilityScore(entity.getAccessibilityScore())
                .environmentScore(entity.getEnvironmentScore())
                .build();
    }

}
