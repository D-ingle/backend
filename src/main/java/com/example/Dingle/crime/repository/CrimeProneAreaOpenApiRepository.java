package com.example.Dingle.crime.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CrimeProneAreaOpenApiRepository {

    @Value("${crime.api.key}")
    private String serviceKey;

    private static final String BASE_URL = "https://www.safetydata.go.kr/V2/api/DSSP-IF-00149";

    public String fetch(int pageNo, int pageSize) throws Exception {

        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&pageNo=").append(pageNo);
        urlBuilder.append("&numOfRows=").append(pageSize);

        URL url = new URI(urlBuilder.toString()).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        connection.disconnect();

        return sb.toString();
    }
}
