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
    private int pathCctvCount;
    private int pathLightCount;

    private List<PoliceModalResponse> polices;
    private boolean nearByCrimeZones;
    private List<String> nearbyCrimeZoneGeoJsons;

}
