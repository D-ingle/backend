package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.CrimeService;
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
    public void saveCrimeProne() throws Exception {
        crimeService.saveCrimeProneArea();
    }
}
