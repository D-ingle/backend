package com.example.Dingle.property.util;

import com.example.Dingle.property.dto.openAI.ConvenienceEvaluationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ConvenienceScoreCalculator {

    private static final int FIVE_MIN = 400;
    private static final int TEN_MIN = 800;
    private static final int TWENTY_MIN = 1500;

    private static final Map<String, Double> WEIGHTS = Map.of(
            "CONVENIENCE_STORE", 0.4,
            "MARKET", 0.3,
            "HOSPITAL", 0.3
    );

    public ConvenienceEvaluationResult calculate(
            Map<String, List<Integer>> distancesByType
    ) {

        List<ConvenienceEvaluationResult.InfraScore> infraScores =
                distancesByType.entrySet().stream()
                        .map(entry -> calculateInfra(
                                entry.getKey(),
                                entry.getValue()
                        ))
                        .toList();

        double weightedSum = infraScores.stream()
                .mapToDouble(score ->
                        score.getScore() *
                                WEIGHTS.getOrDefault(score.getInfraType(), 0.0)
                )
                .sum();

        int convenienceScore = Math.round((float) weightedSum);

        return ConvenienceEvaluationResult.builder()
                .convenienceScore(convenienceScore)
                .infraScores(infraScores)
                .build();
    }

    private ConvenienceEvaluationResult.InfraScore calculateInfra(
            String infraType,
            List<Integer> distances
    ) {

        long countWithin5 = distances.stream()
                .filter(d -> d <= FIVE_MIN)
                .count();

        boolean existsWithin10 = distances.stream()
                .anyMatch(d -> d <= TEN_MIN);

        boolean existsWithin20 = distances.stream()
                .anyMatch(d -> d <= TWENTY_MIN);

        int score;
        if (countWithin5 >= 2) score = 100;
        else if (countWithin5 == 1) score = 80;
        else score = 60;

        if (existsWithin10) score += 10;
        if (existsWithin20) score += 5;

        score = Math.min(score, 100);

        return ConvenienceEvaluationResult.InfraScore.builder()
                .infraType(infraType)
                .countWithin5Min((int) countWithin5)
                .existsWithin10Min(existsWithin10)
                .existsWithin20Min(existsWithin20)
                .score(score)
                .build();
    }
}
