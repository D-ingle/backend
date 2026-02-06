package com.example.Dingle.property.util;

import com.example.Dingle.property.dto.openAI.AccessibilityEvaluationResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessibilityScoreCalculator {

    public AccessibilityEvaluationResult calculate(
            List<Integer> subwayDistances,
            int busDistance
    ) {

        int nearestSubway = subwayDistances.stream()
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);

        int subwayScore =
                nearestSubway <= 300 ? 100 :
                        nearestSubway <= 700 ? 80 : 60;

        if (subwayDistances.size() >= 2) {
            subwayScore += 10;
        }

        subwayScore = Math.min(subwayScore, 100);

        int busScore = busDistance <= 200 ? 100 :
                       busDistance <= 500 ? 80 : 60;

        int accessibilityScore = Math.round(
                (float) (subwayScore * 0.7 + busScore * 0.3)
        );

        return AccessibilityEvaluationResult.builder()
                .accessibilityScore(accessibilityScore)
                .subway(AccessibilityEvaluationResult.Subway.builder()
                        .nearestDistanceMeter(nearestSubway)
                        .stationCount(subwayDistances.size())
                        .score(subwayScore)
                        .build())
                .bus(AccessibilityEvaluationResult.Bus.builder()
                        .distanceMeter(busDistance)
                        .score(busScore)
                        .build())
                .build();
    }
}
