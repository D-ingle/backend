package com.example.Dingle.environment.dto;

import com.example.Dingle.environment.type.NatureType;

public interface EnvironmentTotalRow {
    Long getId();
    String getName();
    Double getLatitude();
    Double getLongitude();
    NatureType getNatureType();
    Double getDistanceMeters();
}
