package com.example.Dingle.environment.controller;

import com.example.Dingle.environment.dto.EnvironmentTotalDTO;
import com.example.Dingle.environment.service.EnvironmentService;
import com.example.Dingle.global.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/total")
    @Operation(summary = "환경 종합 점수 API", description = "환경 종합 점수를 제공합니다.")
    public ResponseEntity<ResponseDTO<EnvironmentTotalDTO>> getEnvironmentTotal(@RequestParam("propertyId") Long propertyId, @RequestParam("distance") int distance) {
        EnvironmentTotalDTO response = environmentService.getEnvironmentTotal(propertyId, distance);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
