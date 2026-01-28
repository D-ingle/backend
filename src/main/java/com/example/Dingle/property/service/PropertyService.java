package com.example.Dingle.property.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.property.dto.DealRequestDto;
import com.example.Dingle.property.dto.PropertyRegisterRequestDto;
import com.example.Dingle.property.entity.*;
import com.example.Dingle.property.repository.*;
import com.example.Dingle.property.type.FacilityType;
import com.example.Dingle.property.type.OptionType;
import com.example.Dingle.realtor.entity.Realtor;
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

    public void register(PropertyRegisterRequestDto request, Realtor realtor, District district) {

        Property property = propertyRepository.save(
                Property.builder()
                        .realtor(realtor)
                        .district(district)
                        .appartmentName(request.getAppartmentName())
                        .address(request.getAddress())
                        .exclusiveArea(request.getExclusiveArea())
                        .supplyArea(request.getSupplyArea())
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
