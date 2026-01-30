package com.example.Dingle.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CctvLocationDto {
    private String district;
    private double latitude;
    private double longitude;
}
