package com.example.Dingle.safety.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SafetyModalResponse {

    private boolean isPassedCrimeZone;
    private int PathCctvCount;
    private int PathLightCount;

    private List<PoliceModalResponse> polices;
    private boolean hasNearbyCrimeZone;
    private List<CrimeAreaModalResponse> nearbyCrimeZones;

}
