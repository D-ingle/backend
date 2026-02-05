package com.example.Dingle.property.util;

import com.example.Dingle.property.dto.openAI.EnvironmentEvaluationResult;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentScoreCalculator {

    private static final double BASE_SLOPE = 10.0;

    private int slopeScore(double slope) {
        return slope <= 5 ? 100 :
               slope <= 8 ? 90 :
               slope <= 10 ? 80 :
               slope <= 13 ? 70 : 65;
    }

    public EnvironmentEvaluationResult calculate(
            double internalSlope,
            double externalSlope,
            int parkDistance,
            int trailDistance
    ) {
        int internalScore = slopeScore(internalSlope);
        int externalScore = slopeScore(externalSlope);

        int combinedSlopeScore = Math.round((float) (0.5 * internalScore + 0.5 * externalScore));

        int parkScore = parkDistance <= 300 ? 100 :
                        parkDistance <= 600 ? 90 :
                        parkDistance <= 1000 ? 80 :
                        parkDistance <= 1500 ? 70 : 60;

        int trailScore = trailDistance <= 200 ? 100 :
                         trailDistance <= 500 ? 90 :
                         trailDistance <= 800 ? 80 :
                         trailDistance <= 1200 ? 70 : 60;

        int greenScore = Math.round((float) (0.6 * parkScore + 0.4 * trailScore));
        int environmentScore = Math.round((float) (0.5 * combinedSlopeScore + 0.5 * greenScore));

        return EnvironmentEvaluationResult.builder()
                .environmentScore(environmentScore)
                .slope(EnvironmentEvaluationResult.Slope.builder()
                        .baseSlopeDegree(BASE_SLOPE)
                        .internalSlopeDegree(internalSlope)
                        .internalSlopeScore(internalScore)
                        .externalSlopeDegree(externalSlope)
                        .externalSlopeScore(externalScore)
                        .combinedSlopeScore(combinedSlopeScore)
                        .build())
                .green(EnvironmentEvaluationResult.Green.builder()
                        .nearestParkDistanceMeter(parkDistance)
                        .parkScore(parkScore)
                        .nearestTrailDistanceMeter(trailDistance)
                        .trailScore(trailScore)
                        .greenScore(greenScore)
                        .build())
                .weights(EnvironmentEvaluationResult.Weights.builder()
                        .internalSlope(0.5)
                        .externalSlope(0.5)
                        .green(0.5)
                        .build())
                .build();
    }
}
