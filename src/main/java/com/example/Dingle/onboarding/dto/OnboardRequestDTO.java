package com.example.Dingle.onboarding.dto;

import com.example.Dingle.property.type.PropertyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardRequestDTO {

    @NotNull
    private PropertyType propertyType;

    @NotNull
    @Size(min = 1, max = 3)
    private List<String> preferredDistricts;

    @NotNull
    @Size(min = 1, max = 3)
    private List<Long> preferredConditions;
}