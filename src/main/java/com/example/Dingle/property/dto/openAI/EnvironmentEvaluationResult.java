package com.example.Dingle.property.dto.openAI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnvironmentEvaluationResult {

    private int environmentScore;

    private Slope slope;
    private Green green;
    private Weights weights;

    @Getter
    @Builder
    public static class Slope {
        private double baseSlopeDegree;
        private double internalSlopeDegree;
        private int internalSlopeScore;
        private double externalSlopeDegree;
        private int externalSlopeScore;
        private int combinedSlopeScore;
    }

    @Getter
    @Builder
    public static class Green {
        private int nearestParkDistanceMeter;
        private int parkScore;
        private int nearestTrailDistanceMeter;
        private int trailScore;
        private int greenScore;
    }

    @Getter
    @Builder
    public static class Weights {
        private double internalSlope;
        private double externalSlope;
        private double green;
    }
}
