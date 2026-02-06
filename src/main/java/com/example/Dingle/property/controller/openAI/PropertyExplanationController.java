package com.example.Dingle.property.controller.openAI;

import com.example.Dingle.property.service.openAI.AccessibilityExplanationService;
import com.example.Dingle.property.service.openAI.ConvenienceExplanationService;
import com.example.Dingle.property.service.openAI.EnvironmentExplanationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class PropertyExplanationController {

    private final EnvironmentExplanationService environmentExplanationService;
    private final ConvenienceExplanationService convenienceExplanationService;
    private final AccessibilityExplanationService accessibilityExplanationService;

    @PostMapping("/{propertyId}/environment")
    @Operation(summary = "환경 점수 측정 API", description = "환경 점수를 측정합니다.")
    public ResponseEntity<Void> evaluateEnvironment(
            @PathVariable Long propertyId
    ) {
        environmentExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/convenience")
    @Operation(summary = "편의 점수 측정 API", description = "편의 점수를 측정합니다.")
    public ResponseEntity<Void> evaluateConvenience(
            @PathVariable Long propertyId
    ) {
        convenienceExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/accessibility")
    @Operation(summary = "접근성 점수 측정 API", description = "접근성 점수를 측정합니다.")
    public ResponseEntity<Void> evaluateAccessibility(
            @PathVariable Long propertyId
    ) {
        accessibilityExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }
}
