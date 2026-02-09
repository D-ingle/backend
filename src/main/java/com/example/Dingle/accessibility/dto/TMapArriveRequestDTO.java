package com.example.Dingle.accessibility.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMapArriveRequestDTO {

    private RoutesInfo routesInfo;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoutesInfo{
        private Point departure;
        private Point destination;
        private String predictionType;
        private String predictionTime;
        private String searchOption;
        private String trafficInfo;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point {
        private String name;
        private Double lon;
        private Double lat;
    }
}
