package com.example.Dingle.environment.controller;

import com.example.Dingle.environment.service.EnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @PostMapping("/waste")
//    @Operation(summary = "하수처리장 데이터 저장 API", description = "하수처리장 데이터를 저장합니다.")
    public void saveWasteFacility(@RequestParam String district) {
        environmentService.saveWasteFacility(district);
    }
}
