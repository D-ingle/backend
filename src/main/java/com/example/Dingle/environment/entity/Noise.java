package com.example.Dingle.environment.entity;

import com.example.Dingle.district.entity.District;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "noise")
public class Noise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    private double noise;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private boolean isWeekend;

    public Noise(
            District district,
            int time,
            double noise,
            double latitude,
            double longitude,
            boolean isWeekend
    ) {
        this.district = district;
        this.time = time;
        this.noise = noise;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isWeekend = isWeekend;
    }
}
