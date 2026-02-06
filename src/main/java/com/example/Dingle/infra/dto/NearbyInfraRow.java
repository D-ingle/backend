package com.example.Dingle.infra.dto;

import com.example.Dingle.infra.type.InfraType;

public interface NearbyInfraRow {
    Long getId();
    String getName();
    InfraType getInfraType();
    Double getLatitude();
    Double getLongitude();
    Double getDistanceMeters();
    String getHospitalType();
}
