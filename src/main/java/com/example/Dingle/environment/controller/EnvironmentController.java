package com.example.Dingle.environment.controller;

import com.example.Dingle.environment.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @PostMapping("/crime")
    public void saveCctvInfra() {
        environmentService.saveCrimePronArea();
    }
}
