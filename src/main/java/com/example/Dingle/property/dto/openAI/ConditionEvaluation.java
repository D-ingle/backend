package com.example.Dingle.property.dto.openAI;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConditionEvaluation {

    private int rank;
    private String conditionName;
    private int weightPercent;
    private String description;
}
