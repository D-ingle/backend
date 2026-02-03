package com.example.Dingle.noise.service.firestation;

import com.example.Dingle.noise.dto.firestation.FireStationLocationDTO;
import com.example.Dingle.noise.dto.firestation.FireStationResponse;
import com.example.Dingle.noise.dto.firestation.FireStationRow;
import com.example.Dingle.noise.repository.FireStationOpenAPIRepository;
import com.example.Dingle.util.map.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private static final int PAGE_SIZE = 1000;

    private final FireStationOpenAPIRepository fireStationOpenAPIRepository;

    public FireStationService(FireStationOpenAPIRepository fireStationOpenAPIRepository) {
        this.fireStationOpenAPIRepository = fireStationOpenAPIRepository;
    }

    public List<FireStationLocationDTO> getFireStationLocations() {

        FireStationResponse firstResponse = fireStationOpenAPIRepository.fetchFireStationData(1, PAGE_SIZE);

        int totalCount = firstResponse.getListTotalCount();
        List<FireStationLocationDTO> result = new ArrayList<>();
        result.addAll(mapRowToLocations(firstResponse.getRow()));

        int startIndex = PAGE_SIZE + 1;

        while(startIndex <= totalCount) {
            int endsIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);

            FireStationResponse response = fireStationOpenAPIRepository.fetchFireStationData(endsIndex, PAGE_SIZE);
            if (response != null && response.getRow() != null) {
                result.addAll(mapRowToLocations(response.getRow()));
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

    private List<FireStationLocationDTO> mapRowToLocations(List<FireStationRow> rows) {
        if( rows == null || rows.isEmpty() ) return List.of();

        return rows.stream()
                .map(this::toLocation)
                .toList();
    }

    private FireStationLocationDTO toLocation(FireStationRow row) {
        Double X = row.getX();
        Double Y = row.getY();

        Double longitude = null;
        Double latitude = null;

        if(X != null && Y != null ){
            double[] lonLat = CoordinateConverter.convert5174(X,Y);
            longitude = lonLat[0];
            latitude = lonLat[1];
        }

        return FireStationLocationDTO.builder()
                .name(row.getName())
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
