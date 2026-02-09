package com.example.Dingle.environment.dto;

import com.example.Dingle.environment.type.NatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentTotalDTO {

    private boolean enabled;
    private Integer environmentScore;
    private int radiusMeters;
    private List<Item> items;
    private ParticulateMatter particulateMatter;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item{
        private NatureType natureType;
        private Long id;
        private String name;
        private double latitude;
        private double longitude;
        private double distanceMeters;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticulateMatter{
        private int pm10;
        private int pm25;
    }
}
