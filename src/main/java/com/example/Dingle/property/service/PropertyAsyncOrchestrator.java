package com.example.Dingle.property.service;

import com.example.Dingle.property.service.openAI.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class PropertyAsyncOrchestrator {
    private final EnvironmentExplanationService environmentExplanationService;
    private final ConvenienceExplanationService convenienceExplanationService;
    private final AccessibilityExplanationService accessibilityExplanationService;
    private final NoiseExplanationService noiseExplanationService;
    private final SafetyExplanationService safetyExplanationService;

    public void process(Long propertyId) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        try {
            Future<Void> f1 = executor.submit(() -> {
                environmentExplanationService.evaluateAndDescribe(propertyId);
                return null;
            });

            Future<Void> f2 = executor.submit(() -> {
                convenienceExplanationService.evaluateAndDescribe(propertyId);
                return null;
            });

            Future<Void> f3 = executor.submit(() -> {
                accessibilityExplanationService.evaluateAndDescribe(propertyId);
                return null;
            });

            Future<Void> f4 = executor.submit(() -> {
                noiseExplanationService.evaluateAndDescribe(propertyId);
                return null;
            });

            Future<Void> f5 = executor.submit(() -> {
                safetyExplanationService.evaluateAndDescribe(propertyId);
                return null;
            });

            f1.get();
            f2.get();
            f3.get();
            f4.get();
            f5.get();

        } catch (Exception e) {
            throw new RuntimeException("Property async processing failed", e);

        } finally {
            executor.close();
        }
    }
}
