package com.example.Dingle.property.controller.openAI;

import com.example.Dingle.property.service.openAI.ConvenienceExplanationService;
import com.example.Dingle.property.service.openAI.EnvironmentExplanationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class PropertyExplanationController {

    private final EnvironmentExplanationService environmentExplanationService;
    private final ConvenienceExplanationService convenienceExplanationService;

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
}
