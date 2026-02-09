package com.example.Dingle.accessibility.service;

import com.example.Dingle.accessibility.dto.TMapArriveDTO;
import com.example.Dingle.accessibility.dto.TMapArriveRequestDTO;
import com.example.Dingle.accessibility.dto.TMapArriveResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TMapPredictionClientService {

    @Qualifier("tMapWebClient")
    private final WebClient tMapWebClient;

    public TMapArriveDTO getPrediction (double startLon, double startLat, double endLon, double endLat, String predictionTime) {

        TMapArriveRequestDTO request = TMapArriveRequestDTO.builder()
                .routesInfo(TMapArriveRequestDTO.RoutesInfo.builder()
                        .departure(TMapArriveRequestDTO.Point.builder()
                                .name("출발")
                                .lon(startLon)
                                .lat(startLat)
                                .build())
                        .destination(TMapArriveRequestDTO.Point.builder()
                                .name("도착")
                                .lon(endLon)
                                .lat(endLat)
                                .build())
                        .predictionType("arrival")
                        .predictionTime(predictionTime)
                        .searchOption("00")
                        .trafficInfo("Y")
                        .build())
                .build();

        TMapArriveResponseDTO response = tMapWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/tmap/routes/prediction")
                        .queryParam("version", 1)
                        .queryParam("reqCoordType", "WGS84GEO")
                        .queryParam("resCoordType", "WGS84GEO")
                        .queryParam("sort", "index")
                        .queryParam("totalValue", 2)
                        .build())
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp -> resp.bodyToMono(String.class)
                        .map(body -> new RuntimeException("TMAP ERROR " + body))
                )
                .bodyToMono(TMapArriveResponseDTO.class)
                .block();

        if(response == null || response.getFeatures() == null || response.getFeatures().isEmpty()
                || response.getFeatures().get(0).getProperties() == null) {
            throw new IllegalStateException("TMAP 응답 형식이 비어있거나 예상과 다릅니다.");
        }

        TMapArriveResponseDTO.Properties p = response.getFeatures().get(0).getProperties();

        if(p.getTotalTime() == null || p.getTotalDistance() == null || p.getDepartureTime() == null || p.getArrivalTime() == null) {
            throw new IllegalStateException("TMAP 응답에 필요한 값이 누락되었습니다: " + p);
        }

        return TMapArriveDTO.builder()
                .totalDistance(p.getTotalDistance())
                .totalTime(p.getTotalTime())
                .departureTime(p.getDepartureTime())
                .arrivalTime(p.getArrivalTime())
                .build();

    }
}
