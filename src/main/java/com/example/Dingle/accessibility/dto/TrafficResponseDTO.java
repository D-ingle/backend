package com.example.Dingle.accessibility.dto;

import com.example.Dingle.infra.type.TrafficType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficResponseDTO {

    private Integer accessibilityScore;
    private List<Bus> buses;
    private List<Subway> subways;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bus{
        private TrafficType trafficType;
        private String name;
        private List<String> busNumber;
        private int distance;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Subway{
        private TrafficType trafficType;
        private String name;
        private int distance;
    }
}
