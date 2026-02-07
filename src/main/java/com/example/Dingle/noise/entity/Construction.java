package com.example.Dingle.noise.entity;

import com.example.Dingle.district.entity.District;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "construction")
public class Construction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    public Construction(District district, String name, LocalDate startDate, LocalDate endDate, Double latitude, Double longitude) {
        this.district = district;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
