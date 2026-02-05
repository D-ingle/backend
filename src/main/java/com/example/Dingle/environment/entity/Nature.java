package com.example.Dingle.environment.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.environment.type.EnvironmentType;
import com.example.Dingle.environment.type.NatureType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "nature")
public class Nature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NatureType natureType;

    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;
}
