package com.example.Dingle.property.controller.openAI;

import com.example.Dingle.property.service.openAI.PropertyExplanationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/openai")
@RequiredArgsConstructor
public class PropertyExplanationController {

    private final PropertyExplanationService propertyExplanationService;

    @PostMapping("/{propertyId}/environment")
    public ResponseEntity<Void> evaluate(
            @PathVariable Long propertyId
    ) {
        propertyExplanationService.evaluateAndDescribe(propertyId);
        return ResponseEntity.ok().build();
    }
}
