package com.example.Dingle.property.dto.openAI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessibilityEvaluationResult {

    private int accessibilityScore;
    private Subway subway;
    private Bus bus;

    @Getter
    @Builder
    public static class Subway {
        private int nearestDistanceMeter;
        private int stationCount;
        private int score;
    }

    @Getter
    @Builder
    public static class Bus {
        private int distanceMeter;
        private int score;
    }
}
