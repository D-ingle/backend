package com.example.Dingle.property.service.openAI;

import com.example.Dingle.noise.repository.FloatingPopulationRepository;
import com.example.Dingle.noise.repository.NoiseRepository;
import com.example.Dingle.property.dto.openAI.MeasuredPoint;
import com.example.Dingle.property.dto.openAI.NoiseEvaluationResult;
import com.example.Dingle.property.dto.openAI.NoiseExplanationPrompt;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.repository.PropertyDescriptionRepository;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import com.example.Dingle.property.util.NoiseScoreCalculator;
import com.example.Dingle.property.util.OpenAiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NoiseExplanationService {
    private final PropertyRepository propertyRepository;
    private final NoiseRepository noiseRepository;
    private final FloatingPopulationRepository flowRepository;
    private final PropertyScoreRepository scoreRepository;
    private final PropertyDescriptionRepository descriptionRepository;

    private final NoiseScoreCalculator calculator;
    private final NoiseExplanationPrompt prompt;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;

    public void evaluateAndDescribe(Long propertyId) throws JsonProcessingException {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow();

        double lat = property.getLatitude();
        double lon = property.getLongitude();

        List<MeasuredPoint> noisePoints =
                noiseRepository.findAll().stream()
                        .map(n -> new MeasuredPoint(
                                n.getLatitude(),
                                n.getLongitude(),
                                n.getNoise()
                        ))
                        .toList();

        List<MeasuredPoint> flowPoints =
                flowRepository.findAll().stream()
                        .map(f -> new MeasuredPoint(
                                f.getLatitude(),
                                f.getLongitude(),
                                f.getPopulation()
                        ))
                        .toList();

        double referenceNoiseAvg = noiseRepository.findDistrictAverage(property.getDistrict().getId());
        double referenceFlowAvg = flowRepository.findDistrictAverage(property.getDistrict().getId());

        NoiseEvaluationResult result = calculator.calculate(lat, lon, noisePoints, flowPoints, referenceNoiseAvg, referenceFlowAvg);

        scoreRepository.findByPropertyId(propertyId)
                .orElseThrow()
                .updateNoiseScore(result.getActivityScore());

        String json = objectMapper.writeValueAsString(result);
        String explanation = openAiClient.generate(prompt.build(result, json));

        descriptionRepository.findByPropertyId(propertyId)
                .orElseThrow()
                .updateNoiseDescription(explanation);
    }
}
