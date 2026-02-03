package com.example.Dingle.infra.dto.cctv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CctvLocationDTO {
    private String district;
    private double latitude;
    private double longitude;
}
