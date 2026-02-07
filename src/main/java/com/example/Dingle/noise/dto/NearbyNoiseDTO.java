package com.example.Dingle.noise.dto;

import com.example.Dingle.noise.NoiseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyNoiseDTO {

    private boolean enabled;
    private Integer noiseScore;
    private int radiusMeters;
    private List<Item> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private NoiseType noiseType;
        private Long id;
        private String name;
        private double latitude;
        private double longitude;
        private Double distanceMeter;

        private LocalDate endDate;
    }


}
