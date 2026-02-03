//package com.example.Dingle.noise.controller;
//
//import com.example.Dingle.noise.service.population.FloatingPopulationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/environment")
//@RequiredArgsConstructor
//public class FloatingPopulationController {
//
//    private final FloatingPopulationService floatingPopulationService;
//
//    @PostMapping("/floating-population")
//    public ResponseEntity<Void> getFloatingPopulation() {
//        floatingPopulationService.getFloatingPopulation();
//        return ResponseEntity.ok().build();
//    }
//}
