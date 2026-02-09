package com.example.Dingle.safety.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmapGeometry {
    private String type;
    private Object coordinates;
}
