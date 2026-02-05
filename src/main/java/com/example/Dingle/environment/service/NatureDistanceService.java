package com.example.Dingle.environment.service;

import com.example.Dingle.environment.dto.NatureDistanceResult;
import com.example.Dingle.environment.entity.Nature;
import com.example.Dingle.environment.repository.NatureRepository;
import com.example.Dingle.environment.type.NatureType;
import com.example.Dingle.property.util.GeoDistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NatureDistanceService {

    private final NatureRepository natureRepository;
    private final GeoDistanceCalculator distanceCalculator;

    public NatureDistanceResult calculateNearestDistances(
            double propertyLat,
            double propertyLng
    ) {
        List<Nature> natures = natureRepository.findAll();

        double nearestPark = Double.MAX_VALUE;
        double nearestTrail = Double.MAX_VALUE;

        for (Nature nature : natures) {
            double dist = distanceCalculator.distanceMeter(
                    propertyLat, propertyLng,
                    nature.getLatitude(), nature.getLongitude()
            );

            if (nature.getNatureType() == NatureType.PARK) {
                nearestPark = Math.min(nearestPark, dist);
            } else if (nature.getNatureType() == NatureType.WALK) {
                nearestTrail = Math.min(nearestTrail, dist);
            }
        }

        return new NatureDistanceResult(
                (int) Math.round(nearestPark),
                (int) Math.round(nearestTrail)
        );
    }
}
