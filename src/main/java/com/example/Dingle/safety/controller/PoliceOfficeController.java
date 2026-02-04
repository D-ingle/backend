package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.PoliceOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/safety")
public class PoliceOfficeController {

    private final PoliceOfficeService policeOfficeService;

    @PostMapping("/police")
    public ResponseEntity<Void> importGuroPoliceOffice() {
        policeOfficeService.savePoliceOffices();
        return ResponseEntity.ok().build();
    }
}
