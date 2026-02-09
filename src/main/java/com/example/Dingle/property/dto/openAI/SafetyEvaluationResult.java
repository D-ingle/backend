package com.example.Dingle.property.dto.openAI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SafetyEvaluationResult {
    private int safetyScore;
    private Home home;
    private Path path;

    @Getter
    @Builder
    public static class Home {
        private int crimeScore;
        private int policeScore;
        private int infraScore;
        private int score;
    }

    @Getter
    @Builder
    public static class Path {
        private int crimeCount;
        private int cctvCount;
        private int safetyLightCount;
        private int score;
    }
}
