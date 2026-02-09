package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.dto.openAI.SafetyEvaluationResult;
import com.example.Dingle.property.dto.openAI.SafetyExplanationPrompt;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyDescription;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.service.PathRouteService;
import com.example.Dingle.property.util.OpenAiClient;
import com.example.Dingle.property.util.SafetyScoreCalculator;
import com.example.Dingle.safety.entity.PropertyPathSafetyItem;
import com.example.Dingle.safety.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SafetyExplanationService {

    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final PropertyPathSafetyItemRepository pathItemRepository;
    private final PropertyDescriptionRepository propertyDescriptionRepository;

    private final HomeSafetyRepository homeSafetyRepository;
    private final PathSafetyRepository pathSafetyRepository;
    private final SafetyScoreCalculator calculator;
    private final PathRouteService pathRouteService;

    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;
    private final SafetyExplanationPrompt prompt;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        double lat = property.getLatitude();
        double lon = property.getLongitude();

        // 집주변 안전 점수
        int crimeScoreHome = calculator.crimeScore(
                homeSafetyRepository.findInsideMaxRiskLevel(lat, lon),
                homeSafetyRepository.existsCrimeWithin300m(lat, lon)
        );

        int policeScore = calculator.policeScore(homeSafetyRepository.findNearestPoliceDistanceMeter(lat, lon));

        int infraScoreHome = calculator.infraScore(
                homeSafetyRepository.countCctvWithin50m(lat, lon),
                homeSafetyRepository.countSafetyLightWithin50m(lat, lon)
        );

        int homeSafetyScore = calculator.safetyScore(crimeScoreHome, policeScore, infraScoreHome);

        // 도보 안전 점수
        String pathWkt = pathRouteService.getPathWkt(property);
        Long pathId = pathSafetyRepository.insertPropertyPath(property.getId(), pathWkt);

        List<Long> cctvIds = pathSafetyRepository.findCctvIdsNearPath(pathWkt);
        List<Long> crimeIds = pathSafetyRepository.findCrimeIdsIntersectPath(pathWkt);
        List<Long> lightIds = pathSafetyRepository.findSafetyLightIdsNearPath(pathWkt);

        int crimeCount = crimeIds.size();
        int cctvCount = cctvIds.size();
        int lightCount = lightIds.size();

        int pathCrimeScore = calculator.pathCrimeScore(crimeCount);
        int pathInfraScore = calculator.pathInfraScore(cctvCount, lightCount);
        int pathSafetyScore = calculator.pathSafetyScore(pathCrimeScore, pathInfraScore);

        PropertyPathSafetyItem summary =
                new PropertyPathSafetyItem(
                        property,
                        crimeCount,
                        cctvCount,
                        lightCount
                );

        pathItemRepository.save(summary);

        pathSafetyRepository.insertCctvMapping(pathId, cctvIds);
        pathSafetyRepository.insertCrimeMapping(pathId, crimeIds);
        pathSafetyRepository.insertSafetyLightMapping(pathId, lightIds);

        // 최종 SAFETY_SCORE
        int finalSafetyScore = (int) Math.round(homeSafetyScore * 0.5 + pathSafetyScore * 0.5);

        PropertyScore score = propertyScoreRepository
                .findByPropertyId(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        score.updateSafetyScore(finalSafetyScore);

        // AI 요약
        SafetyEvaluationResult result = SafetyEvaluationResult.builder()
                        .safetyScore(finalSafetyScore)
                        .home(SafetyEvaluationResult.Home.builder()
                                .crimeScore(crimeScoreHome)
                                .policeScore(policeScore)
                                .infraScore(infraScoreHome)
                                .score(homeSafetyScore)
                                .build())
                        .path(SafetyEvaluationResult.Path.builder()
                                .crimeCount(crimeCount)
                                .cctvCount(cctvCount)
                                .safetyLightCount(lightCount)
                                .score(pathSafetyScore)
                                .build())
                        .build();

        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("SafetyEvaluationResult serialization failed", e);
        }

        String explanation = openAiClient.generate(
                prompt.build(result, json)
        );

        PropertyDescription description = propertyDescriptionRepository.findByPropertyId(propertyId)
                        .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_EXPLANATION_NOT_EXISTS));

        description.updateSafetyDescription(explanation);
    }
}
