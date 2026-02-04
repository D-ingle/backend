package com.example.Dingle.property.dto;

import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCompareDTO {
    private Long propertyId;
    private String address;
    private OrientationType orientation;
    private int bedrooms;
    private int bathrooms;
    private Double parkingRatio;
    private Integer evParkingSpaces;

    private int noiseScore;
    private int safetyScore;
    private int convenienceScore;
    private int accessibilityScore;
    private int environmentScore;

    public static PropertyCompareDTO from(Property property) {
        PropertyScore propertyScore = property.getScore();

        return PropertyCompareDTO.builder()
                .propertyId(property.getId())
                .address(property.getAddress())
                .orientation(property.getOrientation())
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .parkingRatio(property.getParkingRatio())
                .evParkingSpaces(property.getEvParkingSpaces())
                .noiseScore(propertyScore.getNoiseScore())
                .safetyScore(propertyScore.getSafetyScore())
                .convenienceScore(propertyScore.getConvenienceScore())
                .accessibilityScore(propertyScore.getAccessibilityScore())
                .environmentScore(propertyScore.getEnvironmentScore())
                .build();
    }
}
