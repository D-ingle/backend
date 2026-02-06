package com.example.Dingle.infra.dto;

import com.example.Dingle.infra.type.InfraType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyRequestDTO {

    @NotNull
    private Long propertyId;
    private int walkMinutes;
    private Set<InfraType> infraTypes;
}
