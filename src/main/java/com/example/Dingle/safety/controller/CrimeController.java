package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.CrimeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
public class CrimeController {

    private final CrimeService crimeService;

    @PostMapping("/crime")
    @Operation(summary = "범의 구역 데이터 저장 API", description = "범죄 구역을 저장합니다.")
    public void saveCrimeProne() throws Exception {
        crimeService.saveCrimeProneArea();
    }
}
