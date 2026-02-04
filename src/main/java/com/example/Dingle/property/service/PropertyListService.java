package com.example.Dingle.property.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.PropertyListDTO;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PropertyListService {

    private final PropertyRepository propertyRepository;

    public PropertyListService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<PropertyListDTO> getPropertyList(List<Long> propertyIds) {

        List<Property> properties = propertyRepository.findAllByIdInWithDetails(propertyIds);

        Map<Long, Property> propertyMap = properties.stream()
                .collect(Collectors.toMap(Property::getId, p -> p));

        return propertyIds.stream()
                .map(propertyMap::get)
                .filter(Objects::nonNull)
                .map(p -> PropertyListDTO.builder()
                        .propertyId(p.getId())
                        .propertyType(p.getPropertyType())
                        .apartmentName(p.getApartmentName())
                        .exclusiveArea(p.getExclusiveArea())
                        .supplyArea(p.getSupplyArea())
                        .floor(p.getFloor())
                        .totalFloor(p.getTotalFloor())
                        .dealInfo(getDealInfo(p))
                        .build()
                )
                .toList();
    }

    private PropertyListDTO.DealInfo getDealInfo(Property property) {
        if (property.getDeal() == null) {
            throw new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS);
        }

        return switch (property.getDeal().getDealType()) {
            case SALE, LEASE -> new PropertyListDTO.DealInfo(
                    property.getDeal().getDealType(),
                    property.getDeal().getPrice(),
                    null,
                    null
            );
            case RENT -> new PropertyListDTO.DealInfo(
                    property.getDeal().getDealType(),
                    null,
                    property.getDeal().getDeposit(),
                    property.getDeal().getMonthlyRent()
            );
        };
    }

}
