package com.example.Dingle.property.util;

import com.example.Dingle.property.dto.openAI.MeasuredPoint;
import com.example.Dingle.property.dto.openAI.NoiseEvaluationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoiseScoreCalculator {

    private static final double NOISE_WEIGHT = 0.6;
    private static final double FLOW_WEIGHT = 0.4;
    private static final int MAX_RADIUS_METER = 500;

    private final GeoDistanceCalculator geoDistanceCalculator;

    public NoiseEvaluationResult calculate(
            double propertyLat,
            double propertyLon,
            List<MeasuredPoint> noisePoints,
            List<MeasuredPoint> flowPoints,
            double referenceNoiseAvg,
            double referenceFlowAvg
    ) {

        Double nearestNoiseValue = findNearestValue(propertyLat, propertyLon, noisePoints);

        int noiseScore;
        double usedNoiseValue;

        if (nearestNoiseValue == null) {
            usedNoiseValue = referenceNoiseAvg;
            noiseScore = 85;
        } else {
            usedNoiseValue = nearestNoiseValue;
            noiseScore = calculateInverseScore(usedNoiseValue, referenceNoiseAvg);
        }

        Double nearestFlowValue = findNearestValue(propertyLat, propertyLon, flowPoints);

        int flowScore;
        double usedFlowValue;

        if (nearestFlowValue == null) {
            usedFlowValue = referenceFlowAvg;
            flowScore = 85;
        } else {
            usedFlowValue = nearestFlowValue;
            flowScore = calculateInverseScore(usedFlowValue, referenceFlowAvg);
        }

        int activityScore = Math.round((float) (noiseScore * NOISE_WEIGHT + flowScore * FLOW_WEIGHT));

        return NoiseEvaluationResult.builder()
                .activityScore(activityScore)
                .noise(NoiseEvaluationResult.Noise.builder()
                        .weightedAverage(usedNoiseValue)
                        .referenceAverage(referenceNoiseAvg)
                        .score(noiseScore)
                        .build())
                .floatingPopulation(NoiseEvaluationResult.FloatingPopulation.builder()
                        .weightedAverage(usedFlowValue)
                        .referenceAverage(referenceFlowAvg)
                        .score(flowScore)
                        .build())
                .build();
    }

    private Double findNearestValue(
            double lat,
            double lon,
            List<MeasuredPoint> points
    ) {
        double minDistance = Double.MAX_VALUE;
        Double nearestValue = null;

        for (MeasuredPoint p : points) {
            double distance = geoDistanceCalculator.distanceMeter(
                    lat, lon,
                    p.getLatitude(), p.getLongitude()
            );

            if (distance > MAX_RADIUS_METER) continue;

            if (distance < minDistance) {
                minDistance = distance;
                nearestValue = p.getValue();
            }
        }

        return nearestValue;
    }

    private int calculateInverseScore(double local, double reference) {
        if (reference <= 0) return 100;

        double ratio = local / reference;

        if (ratio <= 0.8) return 95;
        if (ratio <= 1.0) return 85;
        if (ratio <= 1.2) return 70;
        if (ratio <= 1.5) return 55;
        return 40;
    }
}
