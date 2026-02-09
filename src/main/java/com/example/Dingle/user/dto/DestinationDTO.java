package com.example.Dingle.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDTO {
    private String destinationName;
    private String destinationAddress;
    private Double destLatitude;
    private Double destLongitude;
}
