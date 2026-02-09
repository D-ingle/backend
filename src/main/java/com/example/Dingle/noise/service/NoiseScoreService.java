package com.example.Dingle.noise.service;

import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.noise.NoiseType;
import com.example.Dingle.noise.dto.*;
import com.example.Dingle.noise.repository.*;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoiseScoreService {

    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;
    private final FireStationRepository fireStationRepository;
    private final EmergencyCenterRepository emergencyCenterRepository;
    private final ConstructionRepository constructionRepository;
    private final NoiseRepository noiseRepository;
    private final FloatingPopulationRepository floatingPopulationRepository;

    public NoiseScoreService(PropertyRepository propertyRepository, PropertyScoreRepository propertyScoreRepository, FireStationRepository fireStationRepository, EmergencyCenterRepository emergencyCenterRepository, ConstructionRepository constructionRepository, NoiseRepository noiseRepository, FloatingPopulationRepository floatingPopulationRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyScoreRepository = propertyScoreRepository;
        this.fireStationRepository = fireStationRepository;
        this.emergencyCenterRepository = emergencyCenterRepository;
        this.constructionRepository = constructionRepository;
        this.noiseRepository = noiseRepository;
        this.floatingPopulationRepository = floatingPopulationRepository;
    }

    public NearbyNoiseDTO getNearbyNoise(Long propertyId, int distance) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyScore propertyScore = propertyScoreRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_SCORE_NOT_FOUND));

        int score = propertyScore.getNoiseScore();

        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        List<NearbyNoiseRow> fireStation = fireStationRepository.findNearbyFireStation(latitude, longitude, distance);
        List<NearbyNoiseRow> emergencyCenter = emergencyCenterRepository.findNearbyEmergencyCenter(latitude, longitude, distance);
        List<NearbyConstructionRow> construction = constructionRepository.findNearbyConstruction(latitude, longitude, distance);

        List<NearbyNoiseDTO.Item> items = new ArrayList<>();

        for(NearbyNoiseRow row : fireStation) {
            items.add(NearbyNoiseDTO.Item.builder()
                    .noiseType(NoiseType.FIRE_STATION)
                    .id(row.getId())
                    .name(row.getName())
                    .latitude(row.getLatitude())
                    .longitude(row.getLongitude())
                    .distanceMeter(row.getDistanceMeters())
                    .build());
        }

        for(NearbyNoiseRow row : emergencyCenter) {
            items.add(NearbyNoiseDTO.Item.builder()
                    .noiseType(NoiseType.EMERGENCY_CENTER)
                    .id(row.getId())
                    .name(row.getName())
                    .latitude(row.getLatitude())
                    .longitude(row.getLongitude())
                    .distanceMeter(row.getDistanceMeters())
                    .build());
        }

        for(NearbyConstructionRow row : construction) {
            items.add(NearbyNoiseDTO.Item.builder()
                    .noiseType(NoiseType.CONSTRUCTION)
                    .id(row.getId())
                    .name(row.getName())
                    .latitude(row.getLatitude())
                    .longitude(row.getLongitude())
                    .distanceMeter(row.getDistanceMeters())
                    .endDate(row.getEndDate())
                    .build());
        }

        return NearbyNoiseDTO.builder()
                .enabled(true)
                .noiseScore(score)
                .radiusMeters(distance)
                .items(items)
                .build();
    }

    public SmartPoleResponseDTO getSmartPole(Long propertyId, int distance, int time, boolean weekend) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        SmartPoleNoiseResponseDTO noise = getSmartPoleNoise(propertyId, distance, time, weekend);
        SmartPolePopulationResponseDTO population = getSmartPolePopulation(propertyId, distance, time, weekend);

        return SmartPoleResponseDTO.builder()
                .propertyId(propertyId)
                .districtId(property.getDistrict().getId())
                .radiusMeters(distance)
                .time(time)
                .weekend(weekend)
                .noise(noise)
                .population(population)
                .build();
    }

    public SmartPoleNoiseResponseDTO getSmartPoleNoise(Long propertyId, int distance, int time, boolean weekend) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        Long districtId = property.getDistrict().getId();

        Double districtAvgNoise = noiseRepository.findDistrictAverageNoise(time, weekend, districtId);

        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        List<SmartPoleNoiseRow> rows = noiseRepository.findNearbyNoiseByTime(latitude, longitude, distance, time, weekend);

        double avgNoise = rows.stream()
                .mapToDouble(SmartPoleNoiseRow::getNoise)
                .average()
                .orElse(Double.NaN);

        Double avgNoiseValue = Double.isNaN(avgNoise) ? null : avgNoise;

        long overCount = rows.stream()
                .filter(row -> row.getNoise() > districtAvgNoise)
                .count();

        List<SmartPoleNoiseResponseDTO.Item> items = rows.stream()
                .map(row -> SmartPoleNoiseResponseDTO.Item.builder()
                        .id(row.getId())
                        .latitude(row.getLatitude())
                        .longitude(row.getLongitude())
                        .noise(row.getNoise())
                        .distanceMeter(row.getDistanceMeter())
                        .build()
                )
                .toList();

        return SmartPoleNoiseResponseDTO.builder()
                .avgNoise(avgNoiseValue)
                .districtAvgNoise(districtAvgNoise)
                .count(items.size())
                .overCount(overCount)
                .items(items)
                .build();
    }

    public SmartPolePopulationResponseDTO getSmartPolePopulation(Long propertyId, int distance, int time, boolean weekend) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        Long districtId = property.getDistrict().getId();

        Double districtAvgPopulation = floatingPopulationRepository.findDistrictAveragePopulation(time, weekend, districtId);

        double latitude = property.getLatitude();
        double longitude = property.getLongitude();

        List<SmartPolePopulationRow> rows = floatingPopulationRepository.findNearbyPopulationByTime(latitude, longitude, distance, time, weekend);

        double avgPopulation = rows.stream()
                .mapToDouble(SmartPolePopulationRow::getPopulation)
                .average()
                .orElse(Double.NaN);

        Double avgPopulationValue = Double.isNaN(avgPopulation) ? null : avgPopulation;

        long overCount = rows.stream()
                .filter(row -> row.getPopulation() > districtAvgPopulation)
                .count();

        List<SmartPolePopulationResponseDTO.Item> items = rows.stream()
                .map(row -> SmartPolePopulationResponseDTO.Item.builder()
                        .id(row.getId())
                        .latitude(row.getLatitude())
                        .longitude(row.getLongitude())
                        .population(row.getPopulation())
                        .distanceMeter(row.getDistanceMeter())
                        .build()
                )
                .toList();

        return SmartPolePopulationResponseDTO.builder()
                .avgPopulation(avgPopulationValue)
                .districtAvgPopulation(districtAvgPopulation)
                .count(items.size())
                .overCount(overCount)
                .items(items)
                .build();
    }
}
