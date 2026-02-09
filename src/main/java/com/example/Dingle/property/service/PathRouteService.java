package com.example.Dingle.property.service;

import com.example.Dingle.global.client.KakaoMapClient;
import com.example.Dingle.global.client.TmapClient;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.safety.dto.TmapGeometry;
import com.example.Dingle.safety.dto.TmapRouteResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PathRouteService {

    private final KakaoMapClient kakaoMapClient;
    private final TmapClient tmapClient;

    public String getPathWkt(Property property) {

        Coordinate station = kakaoMapClient.findNearestSubway(property.getLongitude(), property.getLatitude());

        TmapRouteResponse response = tmapClient.getWalkingRoute(
                station.x, station.y,
                property.getLongitude(), property.getLatitude()
        );

        return toLineStringWkt(response);
    }

    private String toLineStringWkt(TmapRouteResponse response) {

        List<Coordinate> coordinates = new ArrayList<>();

        response.getFeatures().forEach(feature -> {

            TmapGeometry geometry = feature.getGeometry();

            if (!"LineString".equals(geometry.getType())) {
                return;
            }

            @SuppressWarnings("unchecked")
            List<List<Double>> lineCoords =
                    (List<List<Double>>) geometry.getCoordinates();

            lineCoords.forEach(coord -> {
                coordinates.add(new Coordinate(
                        coord.get(0),
                        coord.get(1)
                ));
            });
        });

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        return factory.createLineString(
                coordinates.toArray(new Coordinate[0])
        ).toText();
    }
}
