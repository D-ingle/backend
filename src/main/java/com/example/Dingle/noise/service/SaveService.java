package com.example.Dingle.noise.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.noise.dto.construction.ConstructionLocationDTO;
import com.example.Dingle.noise.dto.emergencyCenter.EmergencyCenterLocationDTO;
import com.example.Dingle.noise.dto.firestation.FireStationLocationDTO;
import com.example.Dingle.noise.entity.Construction;
import com.example.Dingle.noise.entity.EmergencyCenter;
import com.example.Dingle.noise.entity.FireStation;
import com.example.Dingle.noise.repository.ConstructionRepository;
import com.example.Dingle.noise.repository.EmergencyCenterRepository;
import com.example.Dingle.noise.repository.FireStationRepository;
import com.example.Dingle.noise.service.construction.ConstructionService;
import com.example.Dingle.noise.service.emergencyCenter.EmergencyCenterService;
import com.example.Dingle.noise.service.firestation.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final FireStationService fireStationService;
    private final FireStationRepository fireStationRepository;
    private final DistrictRepository districtRepository;
    private final ConstructionService constructionService;
    private final ConstructionRepository constructionRepository;
    private final EmergencyCenterService emergencyCenterService;
    private final EmergencyCenterRepository emergencyCenterRepository;

    @Transactional
    public void saveFireStation() {

        List<FireStationLocationDTO> fireStationLocations = fireStationService.getFireStationLocations();

        List<FireStation> fireStations = fireStationLocations.stream()
                .map(dto -> new FireStation(
                        dto.getName(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();
        fireStationRepository.saveAll(fireStations);
    }

    @Transactional
    public void saveConstruction(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<ConstructionLocationDTO> constructionLocations = constructionService.getConstructionLocations(districtName);

        List<Construction> construction = constructionLocations.stream()
                .map(dto -> new Construction(
                        district,
                        dto.getName(),
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        constructionRepository.saveAll(construction);
    }

    @Transactional
    public void saveEmergencyCenter() {
        List<EmergencyCenterLocationDTO> emergencyCenterLocations = emergencyCenterService.getEmergencyCenterLocation();

        List<EmergencyCenter> emergencyCenters = emergencyCenterLocations.stream()
                .map(dto -> new EmergencyCenter(
                        dto.getName(),
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        emergencyCenterRepository.saveAll(emergencyCenters);
    }

}
