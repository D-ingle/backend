package com.example.Dingle.property.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.DetailPropertyDTO;
import com.example.Dingle.property.entity.*;
import com.example.Dingle.property.repository.*;
import com.example.Dingle.property.type.ImageType;
import com.example.Dingle.realtor.entity.Realtor;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.SavedPropertyRepository;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DetailPropertyService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyOptionRepository propertyOptionRepository;
    private final PropertyFacilityRepository propertyFacilityRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final SavedPropertyRepository savedPropertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    public DetailPropertyService(UserRepository userRepository, PropertyRepository propertyRepository, PropertyOptionRepository propertyOptionRepository, PropertyFacilityRepository propertyFacilityRepository, PropertyScoreRepository propertyScoreRepository, SavedPropertyRepository savedPropertyRepository, PropertyImageRepository propertyImageRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.propertyOptionRepository = propertyOptionRepository;
        this.propertyFacilityRepository = propertyFacilityRepository;
        this.propertyScoreRepository = propertyScoreRepository;
        this.savedPropertyRepository = savedPropertyRepository;
        this.propertyImageRepository = propertyImageRepository;
    }

    public DetailPropertyDTO getDetailProperty(String userId, Long propertyId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyImage floorImage = propertyImageRepository.findByProperty_IdAndImageType(propertyId, ImageType.FLOOR_IMAGE);
        String floorImageUrl = (floorImage != null) ? floorImage.getImageUrl() : null;

        List<PropertyImage> propertyImages = propertyImageRepository.findAllByProperty_IdAndImageType(propertyId, ImageType.PROPERTY);
        List<String> propertyImageUrls = propertyImages.stream()
                .map(PropertyImage::getImageUrl)
                .toList();

        PropertyScore propertyScores = propertyScoreRepository.findAllByPropertyId(propertyId);
        Map<Integer, Integer> scoreMap = Map.of(
                1, propertyScores.getNoiseScore(),
                2, propertyScores.getEnvironmentScore(),
                3, propertyScores.getSafetyScore(),
                4, propertyScores.getAccessibilityScore(),
                5, propertyScores.getConvenienceScore()
        );

        boolean isLiked = savedPropertyRepository.existsByUserIdAndPropertyId(user.getId(), propertyId);

        List<Integer> conditionIds = scoreMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        List<PropertyOption> propertyOptions = propertyOptionRepository.findAllByPropertyId(propertyId);
        List<DetailPropertyDTO.OptionList> optionList = propertyOptions.stream()
                .map(po -> new DetailPropertyDTO.OptionList(
                        po.getOption().getId(),
                        po.getOption().getOptionType()))
                .toList();

        List<PropertyFacility> propertyFacilities = propertyFacilityRepository.findAllByPropertyId(propertyId);
        List<DetailPropertyDTO.FacilityList> facilityList = propertyFacilities.stream()
                .map(pf -> new DetailPropertyDTO.FacilityList(
                        pf.getFacility().getId(),
                        pf.getFacility().getFacilityType()))
                .toList();

        Realtor realtor = property.getRealtor();
        DetailPropertyDTO.RealtorInfo realtorInfo = DetailPropertyDTO.RealtorInfo.builder()
                .realtorId(realtor.getId())
                .username(realtor.getUsername())
                .phone(realtor.getPhone())
                .email(realtor.getEmail())
                .officeName(realtor.getOfficeName())
                .officePhone(realtor.getOfficePhone())
                .officeAddress(realtor.getOfficeAddress())
                .licenseNumber(realtor.getLicenseNumber())
                .build();

        return DetailPropertyDTO.builder()
                .deal(buildDealInfo(property))
                .conditions(conditionIds)
                .propertyInfo(DetailPropertyDTO.PropertyInfo.builder()
                        .propertyType(property.getPropertyType())
                        .apartmentName(property.getApartmentName())
                        .address(property.getAddress())
                        .supplyArea(property.getSupplyArea())
                        .exclusiveArea(property.getExclusiveArea())
                        .bedrooms(property.getBedrooms())
                        .bathrooms(property.getBathrooms())
                        .floor(property.getFloor())
                        .totalFloor(property.getTotalFloor())
                        .orientation(property.getOrientation())
                        .parkingRatio(property.getParkingRatio())
                        .evParkingSpaces(property.getEvParkingSpaces())
                        .liked(isLiked)
                        .longitude(property.getLongitude())
                        .latitude(property.getLatitude())
                        .build())
                .images(DetailPropertyDTO.PropertyImages.builder()
                        .floorImageUrl(floorImageUrl)
                        .propertyImageUrls(propertyImageUrls)
                        .build())
                .option(DetailPropertyDTO.Option.builder()
                        .optionCount(optionList.size())
                        .options(optionList)
                        .build())
                .facility(DetailPropertyDTO.Facility.builder()
                        .facilityCount(facilityList.size())
                        .facilities(facilityList)
                        .build())
                .realtorInfo(realtorInfo)
                .build();
    }

    private DetailPropertyDTO.DealInfo buildDealInfo(Property property) {
        return switch (property.getDeal().getDealType()){
            case SALE, LEASE -> new DetailPropertyDTO.DealInfo(
                    property.getDeal().getDealType(),
                    property.getDeal().getPrice(),
                    null,
                    null
            );
            case RENT -> new DetailPropertyDTO.DealInfo(
                    property.getDeal().getDealType(),
                    null,
                    property.getDeal().getDeposit(),
                    property.getDeal().getMonthlyRent()
            );
        };
    }
}
