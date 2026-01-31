package com.example.Dingle.util.score;

import com.example.Dingle.property.dto.PropertyScoreDTO;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;

public class PropertyScoreMapper {

    private PropertyScoreMapper() {}

    public static PropertyScoreDTO from(Property property) {
        PropertyScore score = property.getScore();

        return PropertyScoreDTO.builder()
                .propertyId(property.getId())
                .noiseScore(score.getNoiseScore())
                .safetyScore(score.getSafetyScore())
                .convenienceScore(score.getConvenienceScore())
                .accessibilityScore(score.getAccessibilityScore())
                .environmentScore(score.getEnvironmentScore())
                .build();
    }
}

