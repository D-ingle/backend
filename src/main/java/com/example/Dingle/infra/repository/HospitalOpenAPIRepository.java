package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.dto.hospital.HospitalResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class HospitalOpenAPIRepository {

    private RestTemplate restTemplate;
    private XmlMapper xmlMapper;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public HospitalOpenAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public HospitalResponse fetchHospitalData(String districtName, int startIndex, int endIndex) {
        StringBuilder url = new StringBuilder("http://openapi.seoul.go.kr:8088/");
        url.append(apiKey)
                .append("/xml/LOCALDATA_010101/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        if (districtName != null && !districtName.isBlank()) {
            url.append(districtName).append("/");
        }

        try {
            String xmlResponse = restTemplate.getForObject(url.toString(), String.class);

            return xmlMapper.readValue(xmlResponse, HospitalResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("서울시 Hospital OpenAPI 호출 실패", e);
        }
    }


}
