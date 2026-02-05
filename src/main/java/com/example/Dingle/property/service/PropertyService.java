package com.example.Dingle.property.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.DealRequestDTO;
import com.example.Dingle.property.dto.PropertyRegisterRequestDTO;
import com.example.Dingle.property.entity.*;
import com.example.Dingle.property.repository.*;
import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.ImageType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.realtor.entity.Realtor;
import com.example.Dingle.realtor.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyDealRepository propertyDealRepository;
    private final OptionRepository optionRepository;
    private final PropertyOptionRepository propertyOptionRepository;
    private final FacilityRepository facilityRepository;
    private final PropertyFacilityRepository propertyFacilityRepository;
    private final RealtorRepository realtorRepository;
    private final DistrictRepository districtRepository;
    private final PropertyImageRepository propertyImageRepository;

    @Transactional
    public void register(PropertyRegisterRequestDTO request) {

        Realtor realtor = realtorRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.REALTOR_NOT_EXISTS));

        District district = districtRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        Property property = propertyRepository.save(
                Property.builder()
                        .realtor(realtor)
                        .district(district)
                        .apartmentName(request.getApartmentName())
                        .address(request.getAddress())
                        .exclusiveArea(request.getExclusiveArea())
                        .supplyArea(request.getSupplyArea())
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .floor(request.getFloor())
                        .totalFloor(request.getTotalFloor())
                        .bedrooms(request.getBedrooms())
                        .bathrooms(request.getBathrooms())
                        .orientation(request.getOrientation())
                        .build()
        );

        saveDeal(property, request.getDeal());
        saveOptions(property, request.getOptions());
        saveFacilities(property, request.getFacilities());
        saveImages(property, request);
    }

    private void saveDeal(Property property, DealRequestDTO deal) {
        PropertyDeal entity = switch (deal.getTradeType()) {
            case SALE -> PropertyDeal.sale(property, deal.getPrice());
            case LEASE -> PropertyDeal.lease(property, deal.getPrice());
            case RENT -> PropertyDeal.rent(property, deal.getDeposit(), deal.getMonthlyPrice());
        };
        propertyDealRepository.save(entity);
    }

    private void saveOptions(Property property, List<OptionType> optionTypes) {
        List<Option> options = optionRepository.findByOptionTypeIn(optionTypes);
        options.forEach(option ->
                propertyOptionRepository.save(new PropertyOption(property, option))
        );
    }

    private void saveFacilities(Property property, List<FacilityType> facilityTypes) {
        List<Facility> facilities = facilityRepository.findByFacilityTypeIn(facilityTypes);
        facilities.forEach(facility ->
                propertyFacilityRepository.save(new PropertyFacility(property, facility))
        );
    }

    private void saveImages(Property property, PropertyRegisterRequestDTO request) {

        if (request.getFloorImageUrl() != null) {
            propertyImageRepository.save(
                    new PropertyImage(
                            property,
                            ImageType.FLOOR_IMAGE,
                            request.getFloorImageUrl()
                    )
            );
        }

        if (request.getPropertyImageUrls() != null) {
            request.getPropertyImageUrls().forEach(url ->
                    propertyImageRepository.save(
                            new PropertyImage(
                                    property,
                                    ImageType.PROPERTY,
                                    url
                            )
                    )
            );
        }
    }
}
