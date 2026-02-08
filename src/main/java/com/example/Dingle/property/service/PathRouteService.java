package com.example.Dingle.property.service;

import com.example.Dingle.global.client.KakaoMapClient;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.safety.dto.KakaoRouteResponse;
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

    public String getPathWkt(Property property) {

        Coordinate station = kakaoMapClient.findNearestSubway(property.getLongitude(), property.getLatitude());

        KakaoRouteResponse response =
                kakaoMapClient.getWalkingRoute(
                        station.x, station.y,
                        property.getLongitude(),
                        property.getLatitude()
                );

        return toLineStringWkt(response);
    }

    private String toLineStringWkt(KakaoRouteResponse response) {

        List<Double> vertexes = new ArrayList<>();

        response.getRoutes().forEach(route ->
                route.getSections().forEach(section ->
                        section.getRoads().forEach(road ->
                                vertexes.addAll(road.getVertexes())
                        )
                )
        );

        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate[] coords = new Coordinate[vertexes.size() / 2];

        for (int i = 0; i < vertexes.size(); i += 2) {
            coords[i / 2] = new Coordinate(
                    vertexes.get(i),
                    vertexes.get(i + 1)
            );
        }

        return factory.createLineString(coords).toText();
    }
}
