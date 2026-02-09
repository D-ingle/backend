package com.example.Dingle.environment.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.environment.dto.EnvironmentTotalDTO;
import com.example.Dingle.environment.dto.EnvironmentTotalRow;
import com.example.Dingle.environment.dto.WasteFacilityLocationDTO;
import com.example.Dingle.environment.entity.Environment;
import com.example.Dingle.environment.entity.ParticulateMatter;
import com.example.Dingle.environment.repository.EnvironmentRepository;
import com.example.Dingle.environment.repository.NatureRepository;
import com.example.Dingle.environment.repository.ParticulateMatterRepository;
import com.example.Dingle.environment.type.EnvironmentType;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnvironmentService {

    private final DistrictRepository districtRepository;
    private final EnvironmentRepository environmentRepository;
    private final WasteService wasteService;
    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final NatureRepository natureRepository;
    private final ParticulateMatterRepository particulateMatterRepository;


    public EnvironmentService(DistrictRepository districtRepository, EnvironmentRepository environmentRepository, WasteService wasteService, PropertyRepository propertyRepository, PropertyScoreRepository propertyScoreRepository, NatureRepository natureRepository, ParticulateMatterRepository particulateMatterRepository) {
        this.districtRepository = districtRepository;
        this.environmentRepository = environmentRepository;
        this.wasteService = wasteService;
        this.propertyRepository = propertyRepository;
        this.propertyScoreRepository = propertyScoreRepository;
        this.natureRepository = natureRepository;
        this.particulateMatterRepository = particulateMatterRepository;
    }

    @Transactional
    public void saveWasteFacility(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        String districtCode = district.getDistrictCode();

        List<WasteFacilityLocationDTO> wasteFacilityLocations = wasteService.getWasteFacilityLocations(districtCode);

        List<Environment> wasteFacility = wasteFacilityLocations.stream()
                .map(dto -> new Environment(
                        district,
                        EnvironmentType.WASTE_TREATMENT,
                        dto.getName(),
                        dto.getLoadAddress(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        environmentRepository.saveAll(wasteFacility);
    }

    public EnvironmentTotalDTO getEnvironmentTotal(Long propertyId, int distance) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyScore propertyScore = propertyScoreRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        int score = propertyScore.getEnvironmentScore();

        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        ParticulateMatter particulateMatter = particulateMatterRepository.findByDistrict_Id(property.getDistrict().getId());

        List<EnvironmentTotalRow> nature = natureRepository.findByNature(latitude, longitude, distance);

        List<EnvironmentTotalDTO.Item> items = new ArrayList<>();

        for(EnvironmentTotalRow row : nature) {
            items.add(EnvironmentTotalDTO.Item.builder()
                    .natureType(row.getNatureType())
                    .id(row.getId())
                    .name(row.getName())
                    .latitude(row.getLatitude())
                    .longitude(row.getLongitude())
                    .distanceMeters(row.getDistanceMeters())
                    .build());
        }

        EnvironmentTotalDTO.ParticulateMatter pm = (particulateMatter == null)
                ? null
                : EnvironmentTotalDTO.ParticulateMatter.builder()
                .pm10(particulateMatter.getPm10() != null ? particulateMatter.getPm10() : 0)
                .pm25(particulateMatter.getPm25() != null ? particulateMatter.getPm25() : 0)
                .build();


        return EnvironmentTotalDTO.builder()
                .enabled(true)
                .environmentScore(score)
                .radiusMeters(distance)
                .items(items)
                .particulateMatter(pm)
                .build();
    }
}
