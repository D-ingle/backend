package com.example.Dingle.property.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculatedScoreDTO {
    private Long propertyId;
    private double score;
}
