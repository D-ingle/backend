package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.service.PathRouteService;
import com.example.Dingle.property.util.SafetyScoreCalculator;
import com.example.Dingle.safety.entity.PropertyPathSafetyItem;
import com.example.Dingle.safety.repository.HomeSafetyRepository;
import com.example.Dingle.safety.repository.PathSafetyRepository;
import com.example.Dingle.safety.repository.PropertyPathSafetyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SafetyExplanationService {

    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final PropertyPathSafetyItemRepository pathItemRepository;

    private final HomeSafetyRepository homeSafetyRepository;
    private final PathSafetyRepository pathSafetyRepository;
    private final SafetyScoreCalculator calculator;
    private final PathRouteService pathRouteService;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(
                        BusinessErrorMessage.PROPERTY_NOT_EXISTS));

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

        // 경로 안전 점수
        String pathWkt = pathRouteService.getPathWkt(property);

        int crimeCount = pathSafetyRepository.countCrimeIntersectPath(pathWkt);
        int cctvCount = pathSafetyRepository.countCctvNearPath(pathWkt);
        int lightCount = pathSafetyRepository.countSafetyLightNearPath(pathWkt);

        int pathCrimeScore = calculator.pathCrimeScore(crimeCount);
        int pathInfraScore = calculator.pathInfraScore(cctvCount, lightCount);
        int pathSafetyScore = calculator.pathSafetyScore(pathCrimeScore, pathInfraScore);

        // 경로 결과 저장
        pathItemRepository.deleteByPropertyId(propertyId);
        pathItemRepository.save(
                new PropertyPathSafetyItem(
                        property,
                        crimeCount,
                        cctvCount,
                        lightCount
                )
        );

        // 최종 SAFETY_SCORE
        int finalSafetyScore = (int) Math.round(homeSafetyScore * 0.5 + pathSafetyScore * 0.5);

        PropertyScore score = propertyScoreRepository
                .findByPropertyId(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        score.updateSafetyScore(finalSafetyScore);
    }
}
