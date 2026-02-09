package com.example.Dingle.safety.dto;

import com.example.Dingle.util.dto.PointDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SafetyRouteResponse {
    private PathDTO path;
    private List<PointDTO> cctvs;
    private List<PointDTO> lights;
}
