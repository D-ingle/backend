package com.example.Dingle.property.dto.openAI;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConvenienceEvaluationResult {

    private int convenienceScore;
    private List<InfraScore> infraScores;

    @Getter
    @Builder
    public static class InfraScore {
        private String infraType;
        private int countWithin5Min;
        private boolean existsWithin10Min;
        private boolean existsWithin20Min;
        private int score;
    }
}
