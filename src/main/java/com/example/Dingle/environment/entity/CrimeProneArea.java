package com.example.Dingle.environment.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.environment.type.CrimeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "crime_prone_area")
@Getter
@NoArgsConstructor
public class CrimeProneArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CrimeType crimeType;

    @Column(nullable = false)
    private int riskLevel;

    @Column(columnDefinition = "geometry(MultiPolygon, 5179)", nullable = false)
    private MultiPolygon geometry;

    public CrimeProneArea(
            District district,
            CrimeType crimeType,
            int riskLevel,
            MultiPolygon geometry
    ) {
        this.district = district;
        this.crimeType = crimeType;
        this.riskLevel = riskLevel;
        this.geometry = geometry;
    }
}
