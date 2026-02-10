package com.example.Dingle.safety.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.repository.PropertyPathRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.safety.dto.*;
import com.example.Dingle.safety.entity.PropertyPath;
import com.example.Dingle.safety.entity.PropertyPathSafetyItem;
import com.example.Dingle.safety.entity.Safety;
import com.example.Dingle.safety.repository.*;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import com.example.Dingle.util.dto.PointDTO;
import com.example.Dingle.util.geojson.GeoJsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SafetyService {

    private final DistrictRepository districtRepository;
    private final SafetyLightService safetyLightService;
    private final SafetyRepository safetyRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyPathSafetyItemRepository propertyPathSafetyItemRepository;
    private final PoliceOfficeRepository policeOfficeRepository;
    private final PathSafetyRepository pathSafetyRepository;
    private final PropertyPathRepository pathRepository;
    private final PropertyPathCctvRepository cctvRepository;
    private final PropertyPathLightRepository lightRepository;
    private final GeoJsonUtil geoJsonUtil;

    @Transactional
    public void saveSafetyLightInfra() {

        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<SafetyLightLocationDTO> safetyLightLocations = safetyLightService.getLightLocations();

        List<Safety> infraList = safetyLightLocations.stream()
                .map(dto -> new Safety(
                        district,
                        InfraType.SAFETY_LIGHT,
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        safetyRepository.saveAll(infraList);
    }

    public SafetyModalResponse getSafetyModal(String userId, Long propertyId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyPathSafetyItem propertyPathSafetyItem = propertyPathSafetyItemRepository.findByProperty_Id(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.SAFETY_ITEM_NOT_EXISTS));

        boolean isPassedCrimeZone = propertyPathSafetyItem.getCrimeProneArea() > 0;

        List<PoliceModalResponse> polices = policeOfficeRepository.findNearbyPolices(property.getLatitude(), property.getLongitude());
        List<String> nearbyCrimeZoneGeoJsons = pathSafetyRepository.findCrimeZonesNearPropertyGeoJson(property.getLatitude(), property.getLongitude());
        boolean nearByCrimeZones = !nearbyCrimeZoneGeoJsons.isEmpty();

        return new SafetyModalResponse(
                isPassedCrimeZone,
                propertyPathSafetyItem.getCctv(),
                propertyPathSafetyItem.getSafetyLight(),
                polices,
                nearByCrimeZones,
                nearbyCrimeZoneGeoJsons
        );
    }

    public SafetyRouteResponse getSafetyRoute(String userId, Long propertyId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_EXISTS));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyPath path = pathRepository.findByProperty_Id(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PATH_NOT_EXISTS));

        Long pathId = path.getId();

        // 1. GeoJSON 조회
        String geoJson = pathRepository.findPathGeoJsonByPathId(pathId);

        // 2. 좌표 변환
        List<PointDTO> pathPoints = geoJsonUtil.toPointList(geoJson);

        // 3. 응답 생성
        return new SafetyRouteResponse(
                new PathDTO(pathPoints),
                cctvRepository.findCctvsByPathId(pathId),
                lightRepository.findLightsByPathId(pathId)
        );
    }
}
