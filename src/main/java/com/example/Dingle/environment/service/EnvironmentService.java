package com.example.Dingle.environment.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.environment.dto.WasteFacilityLocationDTO;
import com.example.Dingle.environment.entity.Environment;
import com.example.Dingle.environment.repository.EnvironmentRepository;
import com.example.Dingle.environment.type.EnvironmentType;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnvironmentService {

    private final DistrictRepository districtRepository;
    private final EnvironmentRepository environmentRepository;
    private final WasteService wasteService;


    public EnvironmentService(DistrictRepository districtRepository, EnvironmentRepository environmentRepository, WasteService wasteService) {
        this.districtRepository = districtRepository;
        this.environmentRepository = environmentRepository;
        this.wasteService = wasteService;
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
}
