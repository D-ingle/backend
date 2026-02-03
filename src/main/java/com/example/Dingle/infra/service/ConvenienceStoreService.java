package com.example.Dingle.infra.service;

import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreLocationDTO;
import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreResponse;
import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreRow;
import com.example.Dingle.infra.repository.ConvenienceStoreAPIRepository;
import com.example.Dingle.util.map.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ConvenienceStoreService {

    private static final int PAGE_SIZE = 1000;
    private static final Pattern GU_PATTERN = Pattern.compile("([가-힣]+구)");

    private final ConvenienceStoreAPIRepository convenienceStoreAPIRepository;

    public ConvenienceStoreService(ConvenienceStoreAPIRepository convenienceStoreAPIRepository) {
        this.convenienceStoreAPIRepository = convenienceStoreAPIRepository;
    }

    public List<ConvenienceStoreLocationDTO> getConvenienceStoreLocations(String districtCode) {

        ConvenienceStoreResponse firstResponse = convenienceStoreAPIRepository.fetchConvenienceStoreData(1, PAGE_SIZE);

        int totalCount = firstResponse.getBody().getTotalCount();

        List<ConvenienceStoreLocationDTO> result = new ArrayList<>();
        result.addAll(mapRowToLocation(extractRows(firstResponse), districtCode));

        int pageNo = 2;
        while((pageNo - 1) * PAGE_SIZE < totalCount) {

            ConvenienceStoreResponse response = convenienceStoreAPIRepository.fetchConvenienceStoreData(pageNo, PAGE_SIZE);

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

    private List<ConvenienceStoreRow> extractRows(ConvenienceStoreResponse response) {
        if (response == null) return List.of();
        if (response.getBody() == null) return List.of();
        if (response.getBody().getItems() == null) return List.of();
        if (response.getBody().getItems().getItem() == null) return List.of();
        return response.getBody().getItems().getItem();
    }


    private List<ConvenienceStoreLocationDTO> mapRowToLocation(List<ConvenienceStoreRow> rows, String districtCode) {
        if(rows == null || rows.isEmpty()) return List.of();

        return rows.stream()
                .filter(row -> districtCode.equals(safe(row.getCode())))
                .filter(row -> row.getName() != null && !row.getName().isBlank())
                .filter(row -> row.getRoadAddress() != null && !row.getRoadAddress().isBlank())

                .map(this::toLocation)
                .toList();
    }

    private ConvenienceStoreLocationDTO toLocation(ConvenienceStoreRow row) {

        Double longitude = null;
        Double latitude = null;

        if(row.getX() != null && row.getY() != null ){
            double[] lonLat = CoordinateConverter.convert3857(row.getX(),row.getY());
            longitude = lonLat[0];
            latitude = lonLat[1];
        }

        return ConvenienceStoreLocationDTO.builder()
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
