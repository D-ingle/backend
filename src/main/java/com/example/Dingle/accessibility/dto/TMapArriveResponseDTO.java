package com.example.Dingle.accessibility.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TMapArriveResponseDTO {

    private List<Feature> features;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Feature {
        private Properties properties;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Properties {
        private Integer totalDistance;
        private Integer totalTime;
        private String departureTime;
        private String arrivalTime;
    }
}
