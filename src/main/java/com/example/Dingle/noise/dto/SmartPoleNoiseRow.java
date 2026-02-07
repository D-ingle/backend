package com.example.Dingle.noise.dto;

public interface SmartPoleNoiseRow {
    Long getId();
    Double getLatitude();
    Double getLongitude();
    Double getNoise();
    Integer getTime();
    Boolean getWeekend();
    Double getDistanceMeter();
}
