package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import com.example.Dingle.property.type.PropertyType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertySearchFilterDTO {

    private String keyword;
    private PropertyType propertyType;

    private List<Long> districtIds;

    private DealType dealType;
    private Long minPrice;
    private Long maxPrice;
    private Long minDeposit;
    private Long maxDeposit;
    private Long minMonthlyRent;
    private Long maxMonthlyRent;

    private Double minExclusiveArea;
    private Double maxExclusiveArea;
}
