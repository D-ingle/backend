package com.example.Dingle.infra.dto.convenienceStore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenienceStoreLocationDTO {
    private String district;
    private String name;
    private String loadAddress;
    private double latitude;
    private double longitude;
}
