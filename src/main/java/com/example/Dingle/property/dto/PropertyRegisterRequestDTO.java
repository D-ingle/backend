package com.example.Dingle.property.dto;


import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.property.type.PropertyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PropertyRegisterRequestDTO {
    private String appartmentName;
    private String address;
    private PropertyType propertyType;

    private int floor;
    private int totalFloor;
    private double exclusiveArea;
    private double supplyArea;
    private double latitude;
    private double longitude;

    private int bedrooms;
    private int bathrooms;
    private OrientationType orientation;

    private List<FacilityType> facilities;
    private List<OptionType> options;
    private DealRequestDTO deal;
}
