package com.example.Dingle.environment.service;

import com.example.Dingle.environment.dto.WasteFacilityLocationDTO;
import com.example.Dingle.environment.dto.WasteFacilityResponse;
import com.example.Dingle.environment.dto.WasteFacilityRow;
import com.example.Dingle.environment.repository.WasteAPIRepository;
import com.example.Dingle.util.map.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WasteService {

    private static final int PAGE_SIZE = 1000;

    private final WasteAPIRepository wasteAPIRepository;

    public WasteService(WasteAPIRepository wasteAPIRepository) {
        this.wasteAPIRepository = wasteAPIRepository;
    }

    public List<WasteFacilityLocationDTO> getWasteFacilityLocations(String districtCode) {

        WasteFacilityResponse firstResponse = wasteAPIRepository.fetchWasteData(1, PAGE_SIZE);

        int totalCount = firstResponse.getBody().getTotalCount();
        List<WasteFacilityLocationDTO> result = new ArrayList<>();
        result.addAll(mapRowToLocation(extractRows(firstResponse), districtCode));

        int pageNo = 2;
        while((pageNo - 1) * PAGE_SIZE < totalCount) {

            WasteFacilityResponse response = wasteAPIRepository.fetchWasteData(pageNo, PAGE_SIZE);
            result.addAll(mapRowToLocation(extractRows(response), districtCode));
            pageNo++;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }

    private List<WasteFacilityRow> extractRows(WasteFacilityResponse response) {
        if (response == null) return List.of();
        if (response.getBody() == null) return List.of();
        if (response.getBody().getItems() == null) return List.of();
        if (response.getBody().getItems().getItem() == null) return List.of();
        return response.getBody().getItems().getItem();
    }

    private List<WasteFacilityLocationDTO> mapRowToLocation( List<WasteFacilityRow> rows, String districtCode) {
        if(rows == null || rows.isEmpty()) return List.of();

        return rows.stream()
                .filter(row -> districtCode.equals(safe(row.getCode())))
                .filter(row -> row.getName() != null && !row.getName().isBlank())
                .filter(row -> row.getRoadAddress() != null && !row.getRoadAddress().isBlank())
                .map(this::toLocation)
                .toList();
    }

    private WasteFacilityLocationDTO toLocation(WasteFacilityRow row) {

        Double longitude = null;
        Double latitude = null;

        if(row.getX() != null && row.getY() != null ){
            double[] lonLat = CoordinateConverter.convert3857(row.getX(),row.getY());
            longitude = lonLat[0];
            latitude = lonLat[1];
        }

        return WasteFacilityLocationDTO.builder()
                .name(row.getName())
                .loadAddress(row.getRoadAddress())
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    private String safe(String v) {
        return v == null ? "" : v.trim();
    }
}
