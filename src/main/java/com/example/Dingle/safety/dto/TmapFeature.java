package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmapFeature {
    private String type;
    private TmapGeometry geometry;
}
