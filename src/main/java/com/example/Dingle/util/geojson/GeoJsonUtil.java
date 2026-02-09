package com.example.Dingle.util.geojson;

import com.example.Dingle.util.dto.PointDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeoJsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<PointDTO> toPointList(String geoJson) {
        try {
            JsonNode root = objectMapper.readTree(geoJson);
            JsonNode coordinates = root.get("coordinates");

            List<PointDTO> points = new ArrayList<>();
            for (JsonNode coord : coordinates) {
                double longitude = coord.get(0).asDouble();
                double latitude = coord.get(1).asDouble();
                points.add(new PointDTO(latitude, longitude));
            }
            return points;

        } catch (Exception e) {
            throw new IllegalArgumentException("GeoJSON 파싱 실패", e);
        }
    }
}
