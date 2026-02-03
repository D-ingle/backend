package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.firestation.FireStationResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class FireStationOpenAPIRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public FireStationOpenAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public FireStationResponse fetchFireStationData(int startIndex, int endIndex) {
        StringBuilder url = new StringBuilder("http://openapi.seoul.go.kr:8088/");
        url.append(apiKey)
                .append("/xml/TbGiWardP/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        try {
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, FireStationResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("서울시 FireStation OpenAPI 호출 실패", e);
        }
    }
}
