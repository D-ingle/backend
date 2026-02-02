package com.example.Dingle.crime.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.crime.type.CrimeType;
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

}
