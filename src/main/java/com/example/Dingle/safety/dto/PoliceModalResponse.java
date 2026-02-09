package com.example.Dingle.safety.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PoliceModalResponse {
    private String policeOfficeName;
    private double latitude;
    private double longitude;
    private double durationTime;
}
