package com.example.Dingle.property.dto.openAI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CurationEvaluationResult {
    private Long propertyId;
    private List<ConditionEvaluation> evaluations;
}
