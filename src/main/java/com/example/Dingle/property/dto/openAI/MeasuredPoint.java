package com.example.Dingle.property.dto.openAI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasuredPoint {
    private double latitude;
    private double longitude;
    private double value;
}
