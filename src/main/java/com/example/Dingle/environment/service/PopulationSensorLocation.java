package com.example.Dingle.environment.service;

import java.util.Map;

public class PopulationSensorLocation {
    public static final Map<String, double[]> SENSOR_MAP = Map.ofEntries(
            Map.entry("00000003000", new double[]{37.502484707, 126.851040478}),
            Map.entry("00000003041", new double[]{37.489467486, 126.884839411}),
            Map.entry("00000003009", new double[]{37.501748618, 126.850769667}),
            Map.entry("00000003017", new double[]{37.488878537, 126.885115720}),
            Map.entry("00000003026", new double[]{37.488216158, 126.885138117})
    );
}
