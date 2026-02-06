package com.example.Dingle.property.service.openAI;

import com.example.Dingle.environment.dto.NatureDistanceResult;
import com.example.Dingle.environment.entity.Slope;
import com.example.Dingle.environment.repository.SlopeRepository;
import com.example.Dingle.environment.service.NatureDistanceService;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.openAI.EnvironmentEvaluationResult;
import com.example.Dingle.property.dto.openAI.EnvironmentExplanationPrompt;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyDescription;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.util.EnvironmentScoreCalculator;
import com.example.Dingle.property.util.OpenAiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EnvironmentExplanationService {

    private final PropertyRepository propertyRepository;
    private final SlopeRepository slopeRepository;
    private final NatureDistanceService natureDistanceService;
    private final EnvironmentScoreCalculator scoreCalculator;
    private final PropertyScoreRepository scoreRepository;
    private final PropertyDescriptionRepository descriptionRepository;
    private final EnvironmentExplanationPrompt prompt;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        Slope slope = slopeRepository.findByPropertyId(propertyId)
                .orElseThrow();

        NatureDistanceResult natureDistance =
                natureDistanceService.calculateNearestDistances(
                        property.getLatitude(),
                        property.getLongitude()
                );

        EnvironmentEvaluationResult result =
                scoreCalculator.calculate(
                        slope.getInternalValue(),
                        slope.getNeighboringValue(),
                        natureDistance.getNearestParkDistanceMeter(),
                        natureDistance.getNearestTrailDistanceMeter()
                );

        PropertyScore score = scoreRepository.findByPropertyId(propertyId)
                .orElseThrow();

        score.updateEnvironmentScore(result.getEnvironmentScore());

        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("EnvironmentEvaluationResult serialization failed", e);
        }

        String explanation;
        try {
            explanation = openAiClient.generate(
                    prompt.build(result, json)
            );
        } catch (Exception e) {
            throw new RuntimeException("request failed", e);
        }

        PropertyDescription description = descriptionRepository
                .findByPropertyId(propertyId)
                .orElseThrow();

        description.updateEnvironmentDescription(explanation);
    }
}
