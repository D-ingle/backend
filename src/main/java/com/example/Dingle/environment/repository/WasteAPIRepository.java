package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.dto.WasteFacilityResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class WasteAPIRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${safemap.openapi.key}")
    private String apiKey;

    public WasteAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public WasteFacilityResponse fetchWasteData(int pageNo, int numOfRows) {

        StringBuilder url = new StringBuilder("https://safemap.go.kr/openapi2/IF_0051");
        url.append("?serviceKey=").append(apiKey)
                .append("&numOfRows=").append(numOfRows)
                .append("&pageNo=").append(pageNo)
                .append("&returnType=XML");

        try {
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, WasteFacilityResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Waste Facility OpenAPI 호출 실패", e);
        }
    }
}
