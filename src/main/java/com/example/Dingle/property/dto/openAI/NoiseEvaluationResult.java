package com.example.Dingle.property.dto.openAI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoiseEvaluationResult {

    private int activityScore;

    private Noise noise;
    private FloatingPopulation floatingPopulation;

    @Getter
    @Builder
    public static class Noise {
        private double weightedAverage;
        private double referenceAverage;
        private int score;
    }

    @Getter
    @Builder
    public static class FloatingPopulation {
        private double weightedAverage;
        private double referenceAverage;
        private int score;
    }
}
