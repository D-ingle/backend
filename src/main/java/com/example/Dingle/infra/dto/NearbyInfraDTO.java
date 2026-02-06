package com.example.Dingle.infra.dto;

import com.example.Dingle.infra.type.InfraType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyInfraDTO {
    private boolean enabled;
    private Integer convenienceScore;
    private int walkMinutes;
    private int radiusMeters;
    private Set<InfraType> infraTypes;
    private List<FacilityItem> facilities;


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FacilityItem {
        private InfraType infraType;
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private Double distanceMeters;
        private String hospitalType;
    }
}
