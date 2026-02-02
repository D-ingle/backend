package com.example.Dingle.crime.controller;

import com.example.Dingle.crime.service.CrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/environment")
public class CrimeController {

    private final CrimeService environmentService;

    @PostMapping("/crime")
    public void saveCctvInfra() throws Exception {
        environmentService.saveCrimeProneArea();
    }
}
