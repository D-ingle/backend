package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import com.example.Dingle.property.type.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListDTO {

    private Long propertyId;
    private PropertyType propertyType;
    private String apartmentName;
    private Double exclusiveArea;
    private Double supplyArea;
    private Integer floor;
    private Integer totalFloor;
    private DealInfo dealInfo;


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
