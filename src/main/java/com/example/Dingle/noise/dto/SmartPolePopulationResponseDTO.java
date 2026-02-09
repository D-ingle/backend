package com.example.Dingle.noise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartPolePopulationResponseDTO {

    private Double avgPopulation;
    private Double districtAvgPopulation;
    private Integer count;
    private Long overCount;
    private List<Item> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private Double latitude;
        private Double longitude;
        private Double population;
        private Double distanceMeter;
    }
}
