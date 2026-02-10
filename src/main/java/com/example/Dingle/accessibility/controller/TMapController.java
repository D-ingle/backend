package com.example.Dingle.accessibility.controller;

import com.example.Dingle.accessibility.dto.TMapArriveDTO;
import com.example.Dingle.accessibility.service.TMapPredictionClientService;
import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/distance")
public class TMapController {

    private final TMapPredictionClientService tMapPredictionClient;

    public TMapController(TMapPredictionClientService tMapPredictionClient) {
        this.tMapPredictionClient = tMapPredictionClient;
    }

    @GetMapping("")
    @Operation(summary = "주요 목적지 소요시간 API", description = "주요 목적지 소요시간을 제공합니다.")
    public ResponseEntity<ResponseDTO<TMapArriveDTO>> getTMapPrediction(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long propertyId,
            @RequestParam String predictionTime
    ) {

        String fixedPredictionTime = predictionTime.replace(" ", "+");

        TMapArriveDTO response = tMapPredictionClient.getPrediction(customUserDetails.getUsername(), propertyId, fixedPredictionTime);

        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
