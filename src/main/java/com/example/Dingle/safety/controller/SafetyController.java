package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.SafetyService;
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
    public void saveSafetyLightInfra() {
        safetyService.saveSafetyLightInfra();
    }
}
