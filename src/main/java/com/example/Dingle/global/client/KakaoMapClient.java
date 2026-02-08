package com.example.Dingle.global.client;

import com.example.Dingle.safety.dto.KakaoPlaceResponse;
import com.example.Dingle.safety.dto.KakaoRouteResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoMapClient {

    @Value("${kakao.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // 가장 가까운 지하철역
    public Coordinate findNearestSubway(double lon, double lat) {

        String url = "https://dapi.kakao.com/v2/local/search/category.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("category_group_code", "SW8")
                .queryParam("x", lon)
                .queryParam("y", lat)
                .queryParam("radius", 1000)
                .queryParam("sort", "distance");

        ResponseEntity<KakaoPlaceResponse> response =
                restTemplate.exchange(
                        uri.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        KakaoPlaceResponse.class
                );

        KakaoPlaceResponse.Document doc =
                response.getBody().getDocuments().get(0);

        return new Coordinate(
                Double.parseDouble(doc.getX()),
                Double.parseDouble(doc.getY())
        );
    }

    // 도보 경로
    public KakaoRouteResponse getWalkingRoute(
            double startLon, double startLat,
            double endLon, double endLat
    ) {

        String url = "https://apis-navi.kakaomobility.com/v1/directions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("origin", startLon + "," + startLat)
                .queryParam("destination", endLon + "," + endLat)
                .queryParam("priority", "RECOMMEND");

        ResponseEntity<KakaoRouteResponse> response =
                restTemplate.exchange(
                        uri.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        KakaoRouteResponse.class
                );

        return response.getBody();
    }
}
