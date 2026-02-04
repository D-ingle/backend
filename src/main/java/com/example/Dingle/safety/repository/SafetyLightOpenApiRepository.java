package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.dto.SafetyOpenLightResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class SafetyLightOpenApiRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    public SafetyLightOpenApiRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    @Value("${seoul.openapi.key}")
    private String apiKey;


    public SafetyOpenLightResponse fetchLightData(
            int startIndex,
            int endIndex
    ) {

        StringBuilder url = new StringBuilder();
        url.append("http://openapi.seoul.go.kr:8088/")
                .append(apiKey)
                .append("/xml/safeOpenCCTV_gr/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        try {
            String xmlResponse = restTemplate.getForObject(url.toString(), String.class);

            return xmlMapper.readValue(
                    xmlResponse,
                    SafetyOpenLightResponse.class
            );

        } catch (Exception e) {
            throw new RuntimeException("서울시 보안등 OpenAPI 호출 실패", e);
        }
    }
}
