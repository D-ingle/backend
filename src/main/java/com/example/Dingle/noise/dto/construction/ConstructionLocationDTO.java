package com.example.Dingle.noise.dto.construction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionLocationDTO {
    private String district;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double latitude;
    private Double longitude;
}
