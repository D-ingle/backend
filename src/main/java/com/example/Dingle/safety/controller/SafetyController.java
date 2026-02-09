package com.example.Dingle.safety.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.property.dto.DetailPropertyDTO;
import com.example.Dingle.safety.dto.SafetyModalResponse;
import com.example.Dingle.safety.service.SafetyService;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/safety")
@RestController
@RequiredArgsConstructor
public class SafetyController {

    private final SafetyService safetyService;

    @PostMapping("/safety-light")
//    @Operation(summary = "보안등 데이터 저장 API", description = "보안등 데이터를 저장합니다.")
    public void saveSafetyLightInfra() {
        safetyService.saveSafetyLightInfra();
    }

    @GetMapping("/{propertyId}/modal")
    @Operation(summary = "지도 안전 모달 조회 API (이거 아직 완성 아님)", description = "안전 모달을 조회합니다.")
    public ResponseEntity<ResponseDTO<SafetyModalResponse>> getSafetyModal(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("propertyId") Long propertyId
    ) {
        SafetyModalResponse response = safetyService.getSafetyModal(userDetails.getUsername(), propertyId);

        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
