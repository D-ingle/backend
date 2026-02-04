package com.example.Dingle.safety.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.dto.cctv.CctvLocationDTO;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.safety.dto.SafetyLightLocationDTO;
import com.example.Dingle.safety.dto.SafetyOpenLightResponse;
import com.example.Dingle.safety.entity.Safety;
import com.example.Dingle.safety.repository.SafetyLightOpenApiRepository;
import com.example.Dingle.safety.repository.SafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SafetyLightService {

    private static final int PAGE_SIZE = 1000;
    private final SafetyLightOpenApiRepository lightOpenApiRepository;

    public List<SafetyLightLocationDTO> getLightLocations() {

        SafetyOpenLightResponse firstResponse = lightOpenApiRepository.fetchLightData(1, PAGE_SIZE);

        int totalCount = firstResponse.getListTotalCount();
        List<SafetyLightLocationDTO> result = new ArrayList<>();

        result.addAll(firstResponse.getRows().stream()
                .map(row -> new SafetyLightLocationDTO(
                        row.getLatitude(),
                        row.getLongitude()
                ))
                .toList()
        );

        int startIndex = PAGE_SIZE + 1;

        while (startIndex <= totalCount) {
            int endIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);

            SafetyOpenLightResponse response = lightOpenApiRepository.fetchLightData(startIndex, endIndex);

            if (response != null && response.getRows() != null) {
                result.addAll(
                        response.getRows().stream()
                                .map(row -> new SafetyLightLocationDTO(
                                        row.getLatitude(),
                                        row.getLongitude()
                                ))
                                .toList()
                );
            }

            startIndex += PAGE_SIZE;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }
}
