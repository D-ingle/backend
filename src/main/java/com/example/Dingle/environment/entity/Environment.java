package com.example.Dingle.environment.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.environment.type.EnvironmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "environment")
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnvironmentType environmentType;

    private String name;
    private String loadAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public Environment(District district, EnvironmentType environmentType, String name, String loadAddress, Double latitude, Double longitude) {
        this.district = district;
        this.environmentType = environmentType;
        this.name = name;
        this.loadAddress = loadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
