package com.example.Dingle.environment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoiseLocationRowDto {
    private String serial;
    private String address;
    private double latitude;
    private double longitude;
}
