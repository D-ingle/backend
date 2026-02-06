package com.example.Dingle.onboarding.controller;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.onboarding.dto.OnboardRequestDTO;
import com.example.Dingle.onboarding.service.OnboardingService;
import com.example.Dingle.user.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class OnboardingController {

    private final OnboardingService onboardingService;

    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping("/onboarding")
    @Operation(summary = "온보딩 API", description = "온보딩을 진행합니다.")
    public ResponseEntity<ResponseDTO<Void>> onboard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody OnboardRequestDTO onboardRequestDTO) {

        onboardingService.saveOnboardInfo(userDetails.getUsername(), onboardRequestDTO);
        return ResponseEntity.ok(ResponseDTO.success(null));
    }
}