package com.example.Dingle.infra.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;

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
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String name;
    private String roadAddress;
    private String hospitalType;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    public Infra(
            District district,
            Category category,
            InfraType infraType,
            Double latitude,
            Double longitude
    ) {
        this.district = district;
        this.category = category;
        this.infraType = infraType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Infra(District district, Category category, InfraType infraType, String name, String loadAddress, Double latitude, Double longitude) {
        this.district = district;
        this.category = category;
        this.infraType = infraType;
        this.name = name;
        this.roadAddress = loadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Infra(District district, Category category, InfraType infraType, String name, String hospitalType, String loadAddress, Double latitude, Double longitude) {
        this.district = district;
        this.category = category;
        this.infraType = infraType;
        this.name = name;
        this.hospitalType = hospitalType;
        this.roadAddress = loadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
