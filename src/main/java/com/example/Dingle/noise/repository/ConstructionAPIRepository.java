package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.dto.construction.ConstructionResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ConstructionAPIRepository {

    private RestTemplate restTemplate;
    private XmlMapper xmlMapper;

    @Value("${safemap.openapi.key}")
    private String apiKey;

    public ConstructionAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public ConstructionResponse fetchConstructionData(String districtName, int pageNo, int numOfRows) {

        StringBuilder url = new StringBuilder("https://safemap.go.kr/openapi2/IF_0043");
        url.append("?serviceKey=").append(apiKey)
                .append("&numOfRows=").append(numOfRows)
                .append("&pageNo=").append(pageNo)
                .append("&returnType=XML");

        try {
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, ConstructionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Construction OpenAPI 호출 실패", e);
        }
    }
}
