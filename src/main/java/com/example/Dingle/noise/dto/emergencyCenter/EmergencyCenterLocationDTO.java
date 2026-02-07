package com.example.Dingle.noise.dto.emergencyCenter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyCenterLocationDTO {
    private String name;
    private Double latitude;
    private Double longitude;
}
