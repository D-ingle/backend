package com.example.Dingle.infra.repository;

import com.example.Dingle.infra.dto.convenienceStore.ConvenienceStoreResponse;
import com.example.Dingle.util.map.OpenApiXmlFetcher;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ConvenienceStoreAPIRepository {

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    @Value("${safemap.openapi.key}")
    private String apiKey;

    public ConvenienceStoreAPIRepository() {
        this.restTemplate = new RestTemplate();
        this.xmlMapper = new XmlMapper();
    }

    public ConvenienceStoreResponse fetchConvenienceStoreData(int pageNo, int numOfRows) {

        StringBuilder url = new StringBuilder("https://safemap.go.kr/openapi2/IF_0039");
        url.append("?serviceKey=").append(apiKey)
                .append("&numOfRows=").append(numOfRows)
                .append("&pageNo=").append(pageNo)
                .append("&returnType=XML");

        try {
            String xmlResponse = OpenApiXmlFetcher.fetch(restTemplate, url.toString());
            return xmlMapper.readValue(xmlResponse, ConvenienceStoreResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("ConvenienceStore OpenAPI 호출 실패", e);
        }
    }
}
