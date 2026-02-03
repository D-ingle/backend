package com.example.Dingle.infra.service;

import com.example.Dingle.infra.dto.cctv.CctvLocationDTO;
import com.example.Dingle.infra.dto.cctv.SafeOpenCctvResponse;
import com.example.Dingle.infra.repository.CctvOpenApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CctvService {

    private static final int PAGE_SIZE = 1000;

    private final CctvOpenApiRepository cctvOpenApiRepository;

    public List<CctvLocationDTO> getCctvLocations(String districtName) {

        SafeOpenCctvResponse firstResponse = cctvOpenApiRepository.fetchCctvData(districtName, 1, PAGE_SIZE);

        int totalCount = firstResponse.getListTotalCount();
        List<CctvLocationDTO> result = new ArrayList<>();

        result.addAll(
                firstResponse.getRows().stream()
                        .map(row -> new CctvLocationDTO(
                                row.getSvcAreaId(),
                                row.getLatitude(),
                                row.getLongitude()
                        ))
                        .toList()
        );

        int startIndex = PAGE_SIZE + 1;

        while (startIndex <= totalCount) {
            int endIndex = Math.min(startIndex + PAGE_SIZE - 1, totalCount);

            SafeOpenCctvResponse response =
                    cctvOpenApiRepository.fetchCctvData(
                            districtName,
                            startIndex,
                            endIndex
                    );

            if (response != null && response.getRows() != null) {
                result.addAll(
                        response.getRows().stream()
                                .map(row -> new CctvLocationDTO(
                                        row.getSvcAreaId(),
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
