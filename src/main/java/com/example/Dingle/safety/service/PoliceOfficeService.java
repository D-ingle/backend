package com.example.Dingle.safety.service;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.district.repository.DistrictRepository;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.safety.dto.PoliceOfficeDTO;
import com.example.Dingle.safety.dto.PoliceOfficeResponse;
import com.example.Dingle.safety.entity.PoliceOffice;
import com.example.Dingle.safety.repository.PoliceOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PoliceOfficeService {

    private final RestTemplate restTemplate;
    private final PoliceOfficeRepository repository;
    private final DistrictRepository districtRepository;

    @Value("${data.openapi.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.odcloud.kr/api/15077036/v1/uddi:6b371c66-09a5-4efd-8445-bfd53672542e";

    public void savePoliceOffices() {

        District district = districtRepository.findByDistrictName("구로구")
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.DISTRICT_NOT_EXISTS));

        int page = 1;
        int perPage = 100;
        boolean hasNext = true;

        while (hasNext) {
            String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("page", page)
                    .queryParam("perPage", perPage)
                    .queryParam("returnType", "json")
                    .queryParam("serviceKey", apiKey)
                    .build()
                    .toUriString();

            PoliceOfficeResponse response = restTemplate.getForObject(url, PoliceOfficeResponse.class);
            if (response == null || response.getData().isEmpty()) break;

            for (PoliceOfficeDTO dto : response.getData()) {
                if (!dto.getAddress().contains("구로구")) {
                    continue;
                }

                repository.save(new PoliceOffice(district, dto.getAddress()));
            }

            page++;
            hasNext = response.getData().size() == perPage;
        }
    }
}
