package com.example.Dingle.property.service.openAI;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.util.SafetyScoreCalculator;
import com.example.Dingle.safety.repository.HomeSafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SafetyExplanationService {

    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final HomeSafetyRepository homeSafetyRepository;
    private final SafetyScoreCalculator calculator;

    public void evaluateAndDescribe(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        double lat = property.getLatitude();
        double lon = property.getLongitude();

        int insideMaxRisk = homeSafetyRepository.findInsideMaxRiskLevel(lat, lon);
        boolean hasCrimeWithin300m = homeSafetyRepository.existsCrimeWithin300m(lat, lon);
        int policeDistance = homeSafetyRepository.findNearestPoliceDistanceMeter(lat, lon);
        int cctv50m = homeSafetyRepository.countCctvWithin50m(lat, lon);
        int light50m = homeSafetyRepository.countSafetyLightWithin50m(lat, lon);

        int crimeScore = calculator.crimeScore(insideMaxRisk, hasCrimeWithin300m);
        int policeScore = calculator.policeScore(policeDistance);
        int infraScore = calculator.infraScore(cctv50m, light50m);
        int safetyScore = calculator.safetyScore(crimeScore, policeScore, infraScore);

        PropertyScore score = propertyScoreRepository
                .findByPropertyId(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        score.updateSafetyScore(safetyScore);
    }
}
