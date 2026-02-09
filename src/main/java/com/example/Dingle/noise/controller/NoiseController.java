package com.example.Dingle.noise.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.noise.dto.NearbyNoiseDTO;
import com.example.Dingle.noise.dto.SmartPoleRequestDTO;
import com.example.Dingle.noise.dto.SmartPoleResponseDTO;
import com.example.Dingle.noise.service.NoiseScoreService;
import com.example.Dingle.noise.service.SaveService;
import com.example.Dingle.noise.service.noise.NoiseService;
import com.example.Dingle.noise.service.population.FloatingPopulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noise")
public class NoiseController {

    private final NoiseService noiseService;
    private final FloatingPopulationService floatingPopulationService;
    private final SaveService saveService;
    private final NoiseScoreService noiseScoreService;

    @PostMapping("")
//    @Operation(summary = "소음 데이터 저장 API", description = "소음 데이터를 저장합니다.")
    public ResponseEntity<Void> getNoise( @RequestParam String date ) {
        noiseService.getNoise(LocalDate.parse(date));
        return ResponseEntity.ok().build();
    }
    @PostMapping("/floating-population")
//    @Operation(summary = "유동인구 데이터 저장 API", description = "유동인구 데이터를 저장합니다.")
    public ResponseEntity<Void> getFloatingPopulation() {
        floatingPopulationService.getFloatingPopulation();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fireStation")
//    @Operation(summary = "소방서 데이터 저장 API", description = "소방서 데이터를 저장합니다.")
    public void saveFireStationInfra() {
        saveService.saveFireStation();
    }

    @PostMapping("/construction")
//    @Operation(summary = "공사 데이터 저장 API", description = "공사 데이터를 저장합니다.")
    public void saveConstructionInfra(@RequestParam String district) {
        saveService.saveConstruction(district);
    }

    @PostMapping("/emergency")
    public void saveEmergencyCenterInfra() { saveService.saveEmergencyCenter(); }

    @GetMapping("/nearby")
    public ResponseEntity<ResponseDTO<NearbyNoiseDTO>> getNearbyNoise(@RequestParam("propertyId") Long propertyId, @RequestParam("distance") int distance) {
        NearbyNoiseDTO response = noiseScoreService.getNearbyNoise(propertyId, distance);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }

    @GetMapping("/smartpole")
    public ResponseEntity<ResponseDTO<SmartPoleResponseDTO>> getSmartPole(@ModelAttribute SmartPoleRequestDTO request) {
        SmartPoleResponseDTO response = noiseScoreService.getSmartPole(
                request.getPropertyId(),
                request.getDistance(),
                request.getTime(),
                request.getWeekend()
        );

        return ResponseEntity.ok(ResponseDTO.success(response));
    }

}
