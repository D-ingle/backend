package com.example.Dingle.environment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteFacilityLocationDTO {
    private String district;
    private String name;
    private String loadAddress;
    private double latitude;
    private double longitude;
}
