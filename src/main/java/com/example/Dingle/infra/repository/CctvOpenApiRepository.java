package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.dto.cctv.SafeOpenCctvResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CctvOpenApiRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public CctvOpenApiRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public SafeOpenCctvResponse fetchCctvData(
            String districtName,
            int startIndex,
            int endIndex
    ) {

        StringBuilder url = new StringBuilder();
        url.append("http://openapi.seoul.go.kr:8088/")
                .append(apiKey)
                .append("/xml/safeOpenCCTV/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        if (districtName != null && !districtName.isBlank()) {
            url.append(districtName).append("/");
        }

        try {
            String xmlResponse =
                    restTemplate.getForObject(url.toString(), String.class);

            return xmlMapper.readValue(
                    xmlResponse,
                    SafeOpenCctvResponse.class
            );

        } catch (Exception e) {
            throw new RuntimeException("서울시 CCTV OpenAPI 호출 실패", e);
        }
    }
}

