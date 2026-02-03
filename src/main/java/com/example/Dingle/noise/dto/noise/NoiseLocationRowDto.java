package com.example.Dingle.noise.dto.noise;

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
