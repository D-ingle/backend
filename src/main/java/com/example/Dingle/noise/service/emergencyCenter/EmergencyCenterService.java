package com.example.Dingle.noise.service.emergencyCenter;

import com.example.Dingle.noise.dto.emergencyCenter.EmergencyCenterLocationDTO;
import com.example.Dingle.noise.dto.emergencyCenter.EmergencyCenterResponse;
import com.example.Dingle.noise.dto.emergencyCenter.EmergencyCenterRow;
import com.example.Dingle.noise.repository.EmergencyCenterAPIRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmergencyCenterService {

    private static final Set<String> TARGET_CENTER_CODES = Set.of( "지역응급의료센터", "권역응급의료센터", "지역응급의료기관");

    private static final int PAGE_SIZE = 1000;

    private final EmergencyCenterAPIRepository emergencyCenterAPIRepository;

    public EmergencyCenterService(EmergencyCenterAPIRepository emergencyCenterAPIRepository) {
        this.emergencyCenterAPIRepository = emergencyCenterAPIRepository;
    }

    public List<EmergencyCenterLocationDTO> getEmergencyCenterLocation() {

        EmergencyCenterResponse firstResponse = emergencyCenterAPIRepository.fetchEmergencyCenterData(1, PAGE_SIZE);

        int totalCount = firstResponse.getListTotalCount();
        List<EmergencyCenterLocationDTO> result = new ArrayList<>();
        result.addAll(mapToLocations(firstResponse.getRow()));

        int startIndex = PAGE_SIZE + 1;

        while(startIndex <= totalCount) {
            int endsIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);

            EmergencyCenterResponse response = emergencyCenterAPIRepository.fetchEmergencyCenterData(startIndex, endsIndex);
            if(response != null && response.getRow() != null) {
                result.addAll(mapToLocations(response.getRow()));
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

    private List<EmergencyCenterLocationDTO> mapToLocations(List<EmergencyCenterRow> rows) {
        if (rows == null || rows.isEmpty()) return List.of();

        return rows.stream()
                .filter(row -> row.getCenterCode() != null)
                .filter(row -> TARGET_CENTER_CODES.contains(row.getCenterCode()))
                .map(this::toLocation)
                .toList();
    }

    private EmergencyCenterLocationDTO toLocation(EmergencyCenterRow row){
        return EmergencyCenterLocationDTO.builder()
                .name(row.getName())
                .latitude(row.getLatitude())
                .longitude(row.getLongitude())
                .build();
    }
}
