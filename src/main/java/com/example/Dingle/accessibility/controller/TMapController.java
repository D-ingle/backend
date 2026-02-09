package com.example.Dingle.accessibility.controller;

import com.example.Dingle.accessibility.dto.TMapArriveDTO;
import com.example.Dingle.accessibility.service.TMapPredictionClientService;
import com.example.Dingle.global.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO<TMapArriveDTO>> getTMapPrediction(
            @RequestParam double startLon,
            @RequestParam double startLat,
            @RequestParam double endLon,
            @RequestParam double endLat,
            @RequestParam String predictionTime
    ) {

        String fixedPredictionTime = predictionTime.replace(" ", "+");

        TMapArriveDTO response = tMapPredictionClient.getPrediction(startLon, startLat, endLon, endLat, fixedPredictionTime);

        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
