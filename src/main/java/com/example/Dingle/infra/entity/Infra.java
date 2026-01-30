package com.example.Dingle.infra.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "infra")
public class Infra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InfraType infraType;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public Infra(
            District district,
            Category category,
            InfraType infraType,
            double latitude,
            double longitude
    ) {
        this.district = district;
        this.category = category;
        this.infraType = infraType;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
