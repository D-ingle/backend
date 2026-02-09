package com.example.Dingle.property.dto;

import com.example.Dingle.property.type.DealType;
import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.property.type.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPropertyDTO {

    private DealInfo deal;
    private List<Integer> conditions;
    private PropertyScore propertyScore;
    private PropertyInfo propertyInfo;
    private PropertyImages images;
    private Option option;
    private Facility facility;
    private RealtorInfo realtorInfo;


    @Getter
    @Builder
    @AllArgsConstructor
    public static class PropertyInfo{
        private PropertyType propertyType;
        private String apartmentName;
        private String address;
        private Double supplyArea;
        private Double exclusiveArea;
        private Integer bedrooms;
        private Integer bathrooms;
        private Integer floor;
        private Integer totalFloor;
        private OrientationType orientation;
        private Double parkingRatio;
        private Integer evParkingSpaces;
        private boolean liked;
        private double latitude;
        private double longitude;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PropertyImages {
        private String floorImageUrl;
        private List<String> propertyImageUrls;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PropertyScore {
        private int noiseScore;
        private int safetyScore;
        private int convenienceScore;
        private int accessibilityScore;
        private int environmentScore;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Option {
        private Integer optionCount;
        private List<OptionList> options;
    }

    @Getter
    @AllArgsConstructor
    public static class OptionList {
        private Long optionId;
        private OptionType optionType;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Facility {
        private Integer facilityCount;
        private List<FacilityList> facilities;
    }

    @Getter
    @AllArgsConstructor
    public static class FacilityList {
        private Long facilityId;
        private FacilityType facilityType;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DealInfo {
        private DealType dealType;
        private Long price;
        private Long deposit;
        private Long monthlyRent;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RealtorInfo {
        private Long realtorId;
        private String username;
        private String phone;
        private String email;
        private String officeName;
        private String officePhone;
        private String officeAddress;
        private String licenseNumber;
    }

}
