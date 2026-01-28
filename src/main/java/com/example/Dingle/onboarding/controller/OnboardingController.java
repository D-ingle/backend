package com.example.Dingle.onboarding.controller;

import com.example.Dingle.global.dto.ResponseDto;
import com.example.Dingle.onboarding.dto.OnboardRequestDTO;
import com.example.Dingle.onboarding.service.OnboardingService;
import com.example.Dingle.user.dto.CustomUserDetails;
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
    public ResponseEntity<ResponseDto<Void>> onboard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody OnboardRequestDTO onboardRequestDTO) {

        onboardingService.saveOnboardInfo(userDetails.getUsername(), onboardRequestDTO);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}
