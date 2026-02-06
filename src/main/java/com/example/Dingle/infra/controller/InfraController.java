package com.example.Dingle.infra.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.infra.dto.NearbyInfraDTO;
import com.example.Dingle.infra.dto.NearbyRequestDTO;
import com.example.Dingle.infra.service.InfraService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/infra")
public class InfraController {

    private final InfraService infraService;

    @PostMapping("/cctv")
//    @Operation(summary = "CCTV 데이터 저장 API", description = "CCTV 데이터를 저장합니다.")
    public void saveCctvInfra(@RequestParam String district) {
        infraService.saveCctvInfra(district);
    }

    @PostMapping("/market")
//    @Operation(summary = "마트 데이터 저장 API", description = "마트 데이터를 저장합니다.")
    public void saveMarketInfra(@RequestParam String district) {
        infraService.saveMarketInfra(district);
    }

    @PostMapping("/hospital")
//    @Operation(summary = "병원 데이터 저장 API", description = "병원 데이터를 저장합니다.")
    public void saveHospitalInfra(@RequestParam String district) {infraService.saveHospitalInfra(district);}

    @PostMapping("/convenienceStore")
//    @Operation(summary = "편의점 데이터 저장 API", description = "편의점 데이터를 저장합니다.")
    public void saveConvenienceStoreInfra(@RequestParam String district) {
        infraService.saveConvenienceStoreInfra(district);
    }

    @GetMapping("/convenience")
    @Operation(summary = "편의 종합 점수 API", description = "편의 점수에 해당하는 시설을 출력합니다.")
    public ResponseEntity<ResponseDTO<NearbyInfraDTO>> getConvenienceInfra(@ModelAttribute NearbyRequestDTO request) {
        NearbyInfraDTO response = infraService.getNearbyInfra(request);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
