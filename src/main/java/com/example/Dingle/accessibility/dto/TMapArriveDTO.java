package com.example.Dingle.accessibility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TMapArriveDTO {
    private int totalDistance;
    private int totalTime;
    private String departureTime;
    private String arrivalTime;
}
