package com.example.Dingle.property.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.PropertyCompareDTO;
import com.example.Dingle.property.dto.PropertyListDTO;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyImage;
import com.example.Dingle.property.repository.PropertyImageRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.type.ImageType;
import com.example.Dingle.user.entity.SavedProperty;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.SavedPropertyRepository;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyListService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final SavedPropertyRepository savedPropertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    public PropertyListService(PropertyRepository propertyRepository, UserRepository userRepository, SavedPropertyRepository savedPropertyRepository, PropertyImageRepository propertyImageRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.savedPropertyRepository = savedPropertyRepository;
        this.propertyImageRepository = propertyImageRepository;
    }

    public List<PropertyListDTO> getPropertyList(List<Long> propertyIds) {

        List<Property> properties = propertyRepository.findAllByIdInWithDetails(propertyIds);

        Map<Long, Property> propertyMap = properties.stream()
                .collect(Collectors.toMap(Property::getId, p -> p));

        List<PropertyImage> images = propertyImageRepository.findAllByProperty_IdInAndImageTypeOrderByProperty_IdAsc(propertyIds, ImageType.PROPERTY);
        Map<Long, String> mainImage = new HashMap<>();
        for(PropertyImage image : images) {
            Long id = image.getProperty().getId();
            mainImage.putIfAbsent(id, image.getImageUrl());
        }

        return propertyIds.stream()
                .map(propertyMap::get)
                .filter(Objects::nonNull)
                .map(p -> PropertyListDTO.builder()
                        .propertyId(p.getId())
                        .propertyType(p.getPropertyType())
                        .imageUrl(mainImage.get(p.getId()))
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

    public List<PropertyListDTO> getLikePropertyList(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        List<SavedProperty> savedProperties = savedPropertyRepository.findAllByUser_Id(user.getId());

        List<Long> propertyIds = savedProperties.stream()
                .map(saved -> saved.getProperty().getId() )
                .toList();

        List<Property> properties = propertyRepository.findAllByIdInWithDetails(propertyIds);

        List<PropertyImage> images = propertyImageRepository.findAllByProperty_IdInAndImageTypeOrderByProperty_IdAsc(propertyIds, ImageType.PROPERTY);
        Map<Long, String> mainImage = new HashMap<>();
        for(PropertyImage image : images) {
            Long id = image.getProperty().getId();
            mainImage.putIfAbsent(id, image.getImageUrl());
        }

        return properties.stream()
                .filter(Objects::nonNull)
                .map(p -> PropertyListDTO.builder()
                        .propertyId(p.getId())
                        .propertyType(p.getPropertyType())
                        .imageUrl(mainImage.get(p.getId()))
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

    public List<PropertyCompareDTO> getPropertyCompare(String userId, List<Long> propertyIds) {

        if(propertyIds == null || propertyIds.isEmpty()) return List.of();

        if(propertyIds.size() > 3) {
            throw new BusinessException(BusinessErrorMessage.EXCEED_COMPARE_LIMIT);
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException(AuthErrorMessage.USER_NOT_EXIST));

        List<Long> savedIds = savedPropertyRepository.findAllByUserIdAndPropertyIdIn(user.getId(), propertyIds)
                .stream()
                .map(saved -> saved.getProperty().getId())
                .toList();

        if(savedIds.size() != propertyIds.size()){
            throw new BusinessException(BusinessErrorMessage.FORBIDDEN_PROPERTY_COMPARE);
        }

        List<Property> properties = propertyRepository.findAllByIdInWithScore(propertyIds);

        Map<Long, Property> propertyMap = properties.stream()
                .collect(Collectors.toMap(Property::getId, p -> p));

        List<PropertyCompareDTO> result = new ArrayList<>();

        for (Long id : propertyIds) {
            Property property = propertyMap.get(id);

            result.add(PropertyCompareDTO.from(property));
        }
        return result;
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
