package com.example.Dingle.safety.controller;

import com.example.Dingle.safety.service.PoliceOfficeService;
import io.swagger.v3.oas.annotations.Operation;
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
//    @Operation(summary = "파출소 데이터 저장 API", description = "파출소 데이터를 저장합니다.")
    public ResponseEntity<Void> importGuroPoliceOffice() {
        policeOfficeService.savePoliceOffices();
        return ResponseEntity.ok().build();
    }
}
