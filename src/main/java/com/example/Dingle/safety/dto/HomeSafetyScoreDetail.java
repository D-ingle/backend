package com.example.Dingle.safety.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeSafetyScoreDetail {
    private int crimeScore;
    private int policeScore;
    private int infraScore;

    private int homeSafetyScore;
}
