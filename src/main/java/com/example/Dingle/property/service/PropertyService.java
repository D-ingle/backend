package com.example.Dingle.property.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.DealRequestDto;
import com.example.Dingle.property.dto.PropertyRegisterRequestDto;
import com.example.Dingle.property.entity.*;
import com.example.Dingle.property.repository.*;
import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.realtor.entity.Realtor;
import com.example.Dingle.realtor.repository.RealtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void register(PropertyRegisterRequestDto request) {

        Realtor realtor = realtorRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        District district = districtRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        Property property = propertyRepository.save(
                Property.builder()
                        .realtor(realtor)
                        .district(district)
                        .appartmentName(request.getAppartmentName())
                        .address(request.getAddress())
                        .exclusiveArea(request.getExclusiveArea())
                        .supplyArea(request.getSupplyArea())
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .floor(request.getFloor())
                        .totalFloor(request.getTotalFloors())
                        .bedrooms(request.getBedrooms())
                        .bathrooms(request.getBathrooms())
                        .orientation(request.getOrientation())
                        .build()
        );

        saveDeal(property, request.getDeal());
        saveOptions(property, request.getOptions());
        saveFacilities(property, request.getFacilities());
    }

    private void saveDeal(Property property, DealRequestDto deal) {
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
}
