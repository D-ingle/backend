package com.example.Dingle.noise.controller;

import com.example.Dingle.noise.service.SaveService;
import com.example.Dingle.noise.service.noise.NoiseService;
import com.example.Dingle.noise.service.population.FloatingPopulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/noise")
public class NoiseController {

    private final NoiseService noiseService;
    private final FloatingPopulationService floatingPopulationService;
    private final SaveService saveService;

    @PostMapping("/")
    public ResponseEntity<Void> getNoise( @RequestParam String date ) {
        noiseService.getNoise(LocalDate.parse(date));
        return ResponseEntity.ok().build();
    }
    @PostMapping("/floating-population")
    public ResponseEntity<Void> getFloatingPopulation() {
        floatingPopulationService.getFloatingPopulation();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fireStation")
    public void saveFireStationInfra() {
        saveService.saveFireStation();
    }

    @PostMapping("/construction")
    public void saveConstructionInfra(@RequestParam String district) {
        saveService.saveConstruction(district);
    }
}
