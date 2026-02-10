package com.example.Dingle.accessibility.controller;

import com.example.Dingle.accessibility.dto.TrafficResponseDTO;
import com.example.Dingle.accessibility.service.TrafficService;
import com.example.Dingle.global.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accessibility")
public class AccessibilityController {

    private final TrafficService trafficService;

    public AccessibilityController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO<TrafficResponseDTO>> getTraffic(@RequestParam Long propertyId) {

        TrafficResponseDTO response = trafficService.getTrafficData(propertyId);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
