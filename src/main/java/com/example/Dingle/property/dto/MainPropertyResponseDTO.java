package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import com.example.Dingle.property.type.PropertyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPropertyResponseDTO {
    private List<PropertyItem> items;
    private Long nextCursor;
    private boolean hasNext;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyItem {
        private Long propertyId;
        private PropertyType propertyType;
        private String apartmentName;
        private Double exclusiveArea;
        private Double supplyArea;
        private Integer floor;
        //private boolean isLiked;
        private double latitude;
        private double longitude;
        private DealInfo dealInfo;
        private List<Long> conditions;

        @JsonIgnore
        private double weightedScore;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DealInfo {
        private DealType dealType;
        private Long price;
        private Long deposit;
        private Long monthlyRent;
    }
}
