package com.example.Dingle.safety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path_crime_area")
public class PropertyPathCrimeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_id", nullable = false)
    private Long pathId;

    @Column(name = "crime_area_id", nullable = false)
    private Long crimeAreaId;

    public PropertyPathCrimeArea(Long pathId, Long crimeAreaId) {
        this.pathId = pathId;
        this.crimeAreaId = crimeAreaId;
    }
}
