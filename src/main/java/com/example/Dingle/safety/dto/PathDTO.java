package com.example.Dingle.safety.dto;

import com.example.Dingle.util.dto.PointDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PathDTO {
    private List<PointDTO> points;
}
