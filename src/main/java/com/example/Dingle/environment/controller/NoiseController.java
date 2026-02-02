package com.example.Dingle.environment.controller;

import com.example.Dingle.environment.service.NoiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
public class NoiseController {

    private final NoiseService noiseService;

    @PostMapping("/noise")
    public ResponseEntity<Void> getNoise(
            @RequestParam String date
    ) {
        noiseService.getNoise(LocalDate.parse(date));
        return ResponseEntity.ok().build();
    }
}
