package com.example.Dingle.noise.dto;

import java.time.LocalDate;

public interface NearbyConstructionRow extends NearbyNoiseRow{
    LocalDate getEndDate();
}
