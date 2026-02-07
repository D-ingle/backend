package com.example.Dingle.noise.dto;

public interface SmartPolePopulationRow {
    Long getId();
    Double getLatitude();
    Double getLongitude();
    Double getPopulation();
    Integer getTime();
    Boolean getWeekend();
    Double getDistanceMeter();
}
