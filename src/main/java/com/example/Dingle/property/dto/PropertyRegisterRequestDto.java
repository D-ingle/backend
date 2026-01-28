package com.example.Dingle.property.dto;


import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.property.type.PropertyType;
import lombok.Getter;

import java.util.List;

@Getter
public class PropertyRegisterRequestDto {
    private String appartmentName;
    private String address;
    private PropertyType propertyType;

    private int floor;
    private int totalFloors;
    private double exclusiveArea;
    private double supplyArea;
    private double latitude;
    private double longitude;

    private int bedrooms;
    private int bathrooms;
    private String orientation;

    private List<FacilityType> facilities;
    private List<OptionType> options;
    private DealRequestDto deal;
}
