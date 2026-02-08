package com.example.Dingle.property.controller.openAI;

import com.example.Dingle.property.service.openAI.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final NoiseExplanationService noiseExplanationService;
    private final SafetyExplanationService safetyExplanationService;

    @PostMapping("/{propertyId}/environment")
    public ResponseEntity<Void> evaluateEnvironment(
            @PathVariable Long propertyId
    ) {
        environmentExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/convenience")
    public ResponseEntity<Void> evaluateConvenience(
            @PathVariable Long propertyId
    ) {
        convenienceExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/accessibility")
    public ResponseEntity<Void> evaluateAccessibility(
            @PathVariable Long propertyId
    ) {
        accessibilityExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/noise")
    public ResponseEntity<Void> evaluateNoise(@PathVariable Long propertyId) throws JsonProcessingException {
        noiseExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{propertyId}/safety")
    public ResponseEntity<Void> evaluateSafety(@PathVariable Long propertyId) {
        safetyExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }
}
