package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.dto.MarketResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class MarketOpenAPIRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${seoul.openapi.key}")
    private String apiKey;

    public MarketOpenAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public MarketResponse fetchMarketData(String districtName, int startIndex, int endIndex) {
        StringBuilder url = new StringBuilder("http://openapi.seoul.go.kr:8088/");
        url.append(apiKey)
                .append("/xml/LOCALDATA_082501/")
                .append(startIndex)
                .append("/")
                .append(endIndex)
                .append("/");

        if (districtName != null && !districtName.isBlank()) {
            url.append(districtName).append("/");
        }

        try {
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, MarketResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("서울시 Market OpenAPI 호출 실패", e);
        }

    }
}
