package com.example.Dingle.global.client;

import com.example.Dingle.safety.dto.TmapRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TmapClient {

    @Value("${tmap.app-key}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public TmapRouteResponse getWalkingRoute(
            double startX, double startY,
            double endX, double endY
    ) {
        String url = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));
        headers.set("appKey", appKey);

        Map<String, Object> body = Map.of(
                "startX", startX,
                "startY", startY,
                "endX", endX,
                "endY", endY,
                "startName", "START",
                "endName", "END"
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<TmapRouteResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        TmapRouteResponse.class
                );

        return response.getBody();
    }
}
