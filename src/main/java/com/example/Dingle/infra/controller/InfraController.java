package com.example.Dingle.infra.controller;

import com.example.Dingle.infra.service.InfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/infra")
public class InfraController {

    private final InfraService infraService;

    @PostMapping("/cctv")
    public void saveCctvInfra(@RequestParam String district) {
        infraService.saveCctvInfra(district);
    }

    @PostMapping("/market")
    public void saveMarketInfra(@RequestParam String district) {
        infraService.saveMarketInfra(district);
    }

    @PostMapping("/hospital")
    public void saveHospitalInfra(@RequestParam String district) {infraService.saveHospitalInfra(district);}

    @PostMapping("/convenienceStore")
    public void saveConvenienceStoreInfra(@RequestParam String district) {
        infraService.saveConvenienceStoreInfra(district);
    }
}
