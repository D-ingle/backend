package com.example.Dingle.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PropertyScoreDTO {
    private Long propertyId;
    private int noiseScore;
    private int safetyScore;
    private int convenienceScore;
    private int accessibilityScore;
    private int environmentScore;

    public int getScoreByConditionId(Long conditionId) {
        return switch (conditionId.intValue()) {
            case 1 -> noiseScore;
            case 2 -> environmentScore;
            case 3 -> safetyScore;
            case 4 -> accessibilityScore;
            case 5 -> convenienceScore;
            default -> 0;
        };
    }
}
