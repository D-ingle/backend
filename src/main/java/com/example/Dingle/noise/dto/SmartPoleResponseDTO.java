package com.example.Dingle.noise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartPoleResponseDTO {

    private Long propertyId;
    private Long districtId;
    private int radiusMeters;
    private int time;
    private Boolean weekend;

    private SmartPoleNoiseResponseDTO noise;
    private SmartPolePopulationResponseDTO population;
}
