package com.example.Dingle.accessibility.service;

import com.example.Dingle.accessibility.dto.TrafficResponseDTO;
import com.example.Dingle.global.exception.BusinessException;
import com.example.Dingle.global.message.BusinessErrorMessage;
import com.example.Dingle.infra.entity.Traffic;
import com.example.Dingle.infra.repository.TrafficRepository;
import com.example.Dingle.infra.type.TrafficType;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.property.entity.PropertyScore;
import com.example.Dingle.property.repository.PropertyRepository;
import com.example.Dingle.property.repository.PropertyScoreRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyScoreRepository propertyScoreRepository;

    public TrafficService(TrafficRepository trafficRepository, PropertyRepository propertyRepository, PropertyScoreRepository propertyScoreRepository) {
        this.trafficRepository = trafficRepository;
        this.propertyRepository = propertyRepository;
        this.propertyScoreRepository = propertyScoreRepository;
    }

    public TrafficResponseDTO getTrafficData(Long propertyId){

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.PROPERTY_NOT_EXISTS));

        PropertyScore propertyScore = propertyScoreRepository.findAllByPropertyId(propertyId);

        List<Traffic> traffics  = trafficRepository.findByProperty_Id(property.getId());

        Map<String, List<Traffic>> busStopMap = traffics.stream()
                .filter(t -> t.getTrafficType() == TrafficType.BUS)
                .filter(t -> t.getTrafficName() != null && !t.getTrafficName().isBlank())
                .collect(Collectors.groupingBy(Traffic::getTrafficName));

        List<TrafficResponseDTO.Bus> buses = busStopMap.entrySet().stream()
                .map(entry -> {
                    String busStopName = entry.getKey();
                    List<Traffic> rows = entry.getValue();

                    List<String> busNumbers = rows.stream()
                            .map(Traffic::getBusNumber)
                            .filter(Objects::nonNull)
                            .map(String::trim)
                            .filter(s -> !s.isBlank())
                            .distinct()
                            .sorted()
                            .toList();

                    Integer distance = rows.stream()
                            .map(Traffic::getDistance)
                            .filter(Objects::nonNull)
                            .min(Integer::compareTo)
                            .orElse(null);

                    Traffic base = rows.get(0);

                    return TrafficResponseDTO.Bus.builder()
                            .trafficType(TrafficType.BUS)
                            .name(busStopName)
                            .latitude(base.getLatitude())
                            .longitude(base.getLongitude())
                            .busNumber(busNumbers)
                            .distance(distance)
                            .build();
                })
                .sorted(Comparator.comparing(
                        TrafficResponseDTO.Bus::getDistance,
                        Comparator.nullsLast(Integer::compareTo)
                ))
                .toList();


        List<TrafficResponseDTO.Subway> subways = traffics.stream()
                .filter(t -> t.getTrafficType() == TrafficType.SUBWAY)
                .filter(t -> t.getTrafficName() != null && !t.getTrafficName().isBlank())
                .map(t -> TrafficResponseDTO.Subway.builder()
                        .trafficType(TrafficType.SUBWAY)
                        .name(t.getTrafficName())
                        .latitude(t.getLatitude())
                        .longitude(t.getLongitude())
                        .distance(t.getDistance())
                        .build())
                .sorted(Comparator.comparing(
                        TrafficResponseDTO.Subway::getDistance,
                        Comparator.nullsLast(Integer::compareTo)
                ))
                .toList();

        return TrafficResponseDTO.builder()
                .accessibilityScore(propertyScore.getAccessibilityScore())
                .buses(buses)
                .subways(subways)
                .build();
    }

}
