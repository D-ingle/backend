package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.util.dto.GeoPointDTO;
import com.example.Dingle.util.dto.GeocodeResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KaKaoGeocodingService {

    private final WebClient kakaoWebClient;

    private KaKaoGeocodingService(@Qualifier("kaKaoWebClient")WebClient kakaoWebClient) {
        this.kakaoWebClient = kakaoWebClient;
    }

    public GeoPointDTO getGeoPoint(String address) {
        GeocodeResponseDTO response = kakaoWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/address.json")
                        .queryParam("query", address)
                        .build()
                )
                .retrieve()
                .bodyToMono(GeocodeResponseDTO.class)
                .block();

        if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            throw new BusinessException(BusinessErrorMessage.INVALID_DESTINATION);
        }

        GeocodeResponseDTO.Document document = response.getDocuments().get(0);

        double lat = Double.parseDouble(document.getY());
        double lng = Double.parseDouble(document.getX());

        return new GeoPointDTO(lat, lng);
    }
}
