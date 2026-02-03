package com.example.Dingle.infra.dto.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalLocationDTO {
    private String district;
    private String name;
    private String hospitalType;
    private String loadAddress;
    private Double latitude;
    private Double longitude;
}
