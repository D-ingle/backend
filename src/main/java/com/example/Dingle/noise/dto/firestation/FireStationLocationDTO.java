package com.example.Dingle.noise.dto.firestation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireStationLocationDTO {
    private String name;
    private Double latitude;
    private Double longitude;
}
