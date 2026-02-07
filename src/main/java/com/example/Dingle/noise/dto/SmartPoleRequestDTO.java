package com.example.Dingle.noise.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SmartPoleRequestDTO {
    private Long propertyId;
    private Integer time;
    private Integer distance;
    private Boolean weekend;
}
