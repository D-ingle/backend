package com.example.Dingle.infra.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.dto.CctvLocationDto;
import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.repository.InfraRepository;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfraService {
    private final CctvService cctvService;
    private final DistrictRepository districtRepository;
    private final InfraRepository infraRepository;

    @Transactional
    public void saveCctvInfra(String districtName) {

        District district = districtRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        List<CctvLocationDto> cctvLocations = cctvService.getCctvLocations(districtName);

        List<Infra> infraList = cctvLocations.stream()
                .map(dto -> new Infra(
                        district,
                        Category.SAFETY,
                        InfraType.CCTV,
                        dto.getLatitude(),
                        dto.getLongitude()
                ))
                .toList();

        infraRepository.saveAll(infraList);
    }
}
