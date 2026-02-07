package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.emergencyCenter.EmergencyCenterResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class EmergencyCenterAPIRepository {

    private RestTemplate restTemplate;
    private XmlMapper xmlMapper;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public EmergencyCenterAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public EmergencyCenterResponse fetchEmergencyCenterData(int startIndex, int endIndex) {
        StringBuilder url = new StringBuilder("http://openapi.seoul.go.kr:8088/");
        url.append(apiKey)
                .append("/xml/TvEmgcHospitalInfo/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        try{
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, EmergencyCenterResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("서울시 Emergency Center OpenAPI 호출 실패", e);
        }
    }
}
