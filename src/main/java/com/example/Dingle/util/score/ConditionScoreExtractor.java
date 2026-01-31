package com.example.Dingle.util.score;

import com.example.Dingle.property.dto.PropertyScoreDTO;

public class ConditionScoreExtractor {
    private ConditionScoreExtractor() {}

    public static int getScore(PropertyScoreDTO dto, long conditionId) {
        return switch ((int) conditionId) {
            case 1 -> dto.getNoiseScore();
            case 2 -> dto.getEnvironmentScore();
            case 3 -> dto.getSafetyScore();
            case 4 -> dto.getAccessibilityScore();
            case 5 -> dto.getConvenienceScore();
            default -> 0;
        };
    }
}
