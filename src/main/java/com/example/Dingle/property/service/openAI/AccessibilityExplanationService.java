package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.entity.Traffic;
import com.example.Dingle.infra.repository.TrafficRepository;
import com.example.Dingle.infra.type.TrafficType;
import com.example.Dingle.property.dto.openAI.AccessibilityEvaluationResult;
import com.example.Dingle.property.dto.openAI.AccessibilityExplanationPrompt;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyDescription;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.util.AccessibilityScoreCalculator;
import com.example.Dingle.property.util.OpenAiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccessibilityExplanationService {

    private final PropertyRepository propertyRepository;
    private final TrafficRepository trafficRepository;
    private final AccessibilityScoreCalculator scoreCalculator;
    private final PropertyScoreRepository scoreRepository;
    private final PropertyDescriptionRepository descriptionRepository;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;
    private final AccessibilityExplanationPrompt prompt;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        List<Traffic> traffics = trafficRepository.findByPropertyId(propertyId);

        List<Integer> subwayDistances = traffics.stream()
                .filter(t -> t.getTrafficType() == TrafficType.SUBWAY)
                .map(Traffic::getDistance)
                .toList();

        int busDistance = traffics.stream()
                .filter(t -> t.getTrafficType() == TrafficType.BUS)
                .findFirst()
                .map(Traffic::getDistance)
                .orElse(Integer.MAX_VALUE);

        AccessibilityEvaluationResult result =
                scoreCalculator.calculate(subwayDistances, busDistance);

        PropertyScore score = scoreRepository.findByPropertyId(propertyId)
                .orElseThrow();
        score.updateAccessibilityScore(result.getAccessibilityScore());

        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("AccessibilityEvaluationResult serialization failed", e);
        }

        String explanation = openAiClient.generate(
                prompt.build(result, json)
        );

        PropertyDescription description =
                descriptionRepository.findByPropertyId(propertyId)
                        .orElseThrow();

        description.updateAccessibilityDescription(explanation);
    }
}
