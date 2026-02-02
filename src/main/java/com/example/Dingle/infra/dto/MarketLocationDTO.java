package com.example.Dingle.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketLocationDTO {
    private String district;
    private String name;
    private String loadAddress;
    private double latitude;
    private double longitude;
}
