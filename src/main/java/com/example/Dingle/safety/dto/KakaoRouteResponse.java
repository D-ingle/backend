package com.example.Dingle.safety.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoRouteResponse {

    private List<Route> routes;

    @Getter
    @NoArgsConstructor
    public static class Route {
        private List<Section> sections;
    }

    @Getter
    @NoArgsConstructor
    public static class Section {
        private List<Road> roads;
    }

    @Getter
    @NoArgsConstructor
    public static class Road {
        private List<Double> vertexes;
    }
}
