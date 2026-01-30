package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.dto.CrimeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
@RequiredArgsConstructor
public class CrimeProneAreaOpenApiRepository {

    private final RestTemplate restTemplate;

    @Value("${crime.api.key}")
    private String serviceKey;

    private static final String BASE_URL = "https://apis.data.go.kr/V2/api/DSSP-IF-00149";

    public CrimeApiResponse fetch(int pageNo, int pageSize) {

        String url = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", pageSize)
                .queryParam("pageNo", pageNo)
                .queryParam("returnType", "json")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, CrimeApiResponse.class);
    }
}
