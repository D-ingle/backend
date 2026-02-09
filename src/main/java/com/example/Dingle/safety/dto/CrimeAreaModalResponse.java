package com.example.Dingle.safety.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CrimeAreaModalResponse {
    private Long id;
    private String crimeType;
    private int riskLevel;
    private String geoJson;
}
