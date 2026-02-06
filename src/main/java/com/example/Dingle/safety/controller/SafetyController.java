package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.SafetyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/safety")
@RestController
@RequiredArgsConstructor
public class SafetyController {

    private final SafetyService safetyService;

    @PostMapping("/safety-light")
//    @Operation(summary = "보안등 데이터 저장 API", description = "보안등 데이터를 저장합니다.")
    public void saveSafetyLightInfra() {
        safetyService.saveSafetyLightInfra();
    }
}
