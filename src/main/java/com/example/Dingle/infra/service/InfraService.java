package com.example.Dingle.infra.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.dto.NearbyInfraDTO;
import com.example.Dingle.infra.dto.NearbyInfraRow;
import com.example.Dingle.infra.dto.cctv.CctvLocationDTO;
import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreLocationDTO;
import com.example.Dingle.infra.dto.hospital.HospitalLocationDTO;
import com.example.Dingle.infra.dto.market.MarketLocationDTO;
import com.example.Dingle.infra.dto.NearbyRequestDTO;
import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.repository.InfraRepository;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.safety.entity.Safety;
import com.example.Dingle.safety.repository.SafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InfraService {
    private final CctvService cctvService;
    private final MarketService marketService;
    private final HospitalService hospitalService;
    private final DistrictRepository districtRepository;
    private final InfraRepository infraRepository;
    private final ConvenienceStoreService convenienceStoreService;
    private final SafetyRepository safetyRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;

    @Transactional
    public void saveCctvInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<CctvLocationDTO> cctvLocations = cctvService.getCctvLocations(districtName);

        List<Safety> infraList = cctvLocations.stream()
                .map(dto -> new Safety(
                        district,
                        InfraType.CCTV,
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        safetyRepository.saveAll(infraList);
    }

    @Transactional
    public void saveMarketInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<MarketLocationDTO> marketLocations = marketService.getMarketLocations(districtName);

        List<Infra> market = marketLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.CONVENIENCE,
                        InfraType.MARKET,
                        dto.getName(),
                        dto.getLoadAddress(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();
        infraRepository.saveAll(market);
    }

    @Transactional
    public void saveConvenienceStoreInfra(String districtName) {
        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        String districtCode = district.getDistrictCode();

        List<ConvenienceStoreLocationDTO> convenienceStoreLocations = convenienceStoreService.getConvenienceStoreLocations(districtCode);

        List<Infra> convenienceStore = convenienceStoreLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.CONVENIENCE,
                        InfraType.CONVENIENCE_STORE,
                        dto.getName(),
                        dto.getLoadAddress(),
                        dto.getLongitude(),
                        dto.getLatitude()
                ))
                .toList();
        infraRepository.saveAll(convenienceStore);
    }
    
    public void saveHospitalInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<HospitalLocationDTO> hospitalLocations = hospitalService.getHospitalLocations(districtName);

        List<Infra> hospital = hospitalLocations.stream()
                .filter(dto -> dto.getLatitude() != null && dto.getLongitude() != null)
                .map(dto -> new Infra(
                        district,
                        Category.CONVENIENCE,
                        InfraType.HOSPITAL,
                        dto.getName(),
                        dto.getHospitalType(),
                        dto.getLoadAddress(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();
        infraRepository.saveAll(hospital);
    }

    public NearbyInfraDTO getNearbyInfra(NearbyRequestDTO requestDTO) {

        Property property = propertyRepository.findById(requestDTO.getPropertyId())
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyScore propertyScore = propertyScoreRepository.findByPropertyId(property.getId())
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        int score = propertyScore.getConvenienceScore();

        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        int walkMinutes = defaultWalk(requestDTO.getWalkMinutes());
        Set<InfraType> infraTypes = defaultInfraTypes(requestDTO.getInfraTypes());
        int radiusMeters = walkToRadiusMeters(walkMinutes);

        List<String> infraTypeNames = infraTypes.stream().map(Enum::name).toList();

        List<NearbyInfraRow> rows = infraRepository.findNearbyInfra(
                latitude,
                longitude,
                radiusMeters,
                infraTypeNames
        );

        List<NearbyInfraDTO.FacilityItem> facilities = rows.stream()
                .map(row -> NearbyInfraDTO.FacilityItem.builder()
                        .infraType(row.getInfraType())
                        .hospitalType(row.getHospitalType())
                        .id(row.getId())
                        .name(row.getName())
                        .roadAddress(row.getRoadAddress())
                        .latitude(row.getLatitude())
                        .longitude(row.getLongitude())
                        .distanceMeters(row.getDistanceMeters())
                        .build()
                )
                .toList();

        return NearbyInfraDTO.builder()
                .enabled(true)
                .convenienceScore(score)
                .walkMinutes(walkMinutes)
                .radiusMeters(radiusMeters)
                .infraTypes(infraTypes)
                .facilities(facilities)
                .build();
    }

    private int defaultWalk(int walkMinutes) {
        return (walkMinutes == 5 || walkMinutes == 10 || walkMinutes == 20) ? walkMinutes : 5;
    }

    private Set<InfraType> defaultInfraTypes(Set<InfraType> infraTypes) {
        return (infraTypes == null || infraTypes.isEmpty()) ? Set.of(InfraType.CONVENIENCE_STORE) : infraTypes;
    }

    private int walkToRadiusMeters(int walkMinutes) {
        return switch (walkMinutes) {
            case 5 -> 330;
            case 10 -> 670;
            case 20 -> 800;
            default -> 330;
        };
    }
}
