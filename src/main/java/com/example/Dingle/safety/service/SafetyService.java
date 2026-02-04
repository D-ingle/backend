package com.example.Dingle.safety.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.safety.dto.SafetyLightLocationDTO;
import com.example.Dingle.safety.entity.Safety;
import com.example.Dingle.safety.repository.SafetyRepository;
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
}
