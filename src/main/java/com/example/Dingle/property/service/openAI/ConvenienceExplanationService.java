package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.entity.Infra;
import com.example.Dingle.infra.repository.InfraRepository;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.property.dto.openAI.ConvenienceEvaluationResult;
import com.example.Dingle.property.dto.openAI.ConvenienceExplanationPrompt;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyDescription;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.util.ConvenienceScoreCalculator;
import com.example.Dingle.property.util.GeoDistanceCalculator;
import com.example.Dingle.property.util.OpenAiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConvenienceExplanationService {

    private final PropertyRepository propertyRepository;
    private final InfraRepository infraRepository;
    private final GeoDistanceCalculator geoDistanceCalculator;
    private final ConvenienceScoreCalculator scoreCalculator;
    private final PropertyScoreRepository scoreRepository;
    private final PropertyDescriptionRepository descriptionRepository;
    private final ConvenienceExplanationPrompt prompt;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        double lat = property.getLatitude();
        double lon = property.getLongitude();

        List<Infra> infraList = infraRepository.findByInfraTypeIn(
                List.of(
                        InfraType.CONVENIENCE_STORE,
                        InfraType.MARKET,
                        InfraType.HOSPITAL
                )
        );

        Map<String, List<Integer>> distancesByType =
                infraList.stream()
                        .collect(Collectors.groupingBy(
                                infra -> infra.getInfraType().name(),
                                Collectors.mapping(
                                        infra -> (int) (
                                                geoDistanceCalculator.distanceMeter(
                                                        lat, lon,
                                                        infra.getLatitude(),
                                                        infra.getLongitude()
                                                ) * 1.2
                                        ),
                                        Collectors.toList()
                                )
                        ));

        ConvenienceEvaluationResult result =
                scoreCalculator.calculate(distancesByType);

        PropertyScore score = scoreRepository.findByPropertyId(propertyId)
                .orElseThrow();
        score.updateConvenienceScore(result.getConvenienceScore());

        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("ConvenienceEvaluationResult serialization failed", e);
        }

        String explanation;
        try {
            explanation = openAiClient.generate(
                    prompt.build(result, json)
            );
        } catch (Exception e) {
            throw new RuntimeException("OpenAI request failed", e);
        }

        PropertyDescription description =
                descriptionRepository.findByPropertyId(propertyId)
                        .orElseThrow();

        description.updateConvenienceDescription(explanation);
    }
}
