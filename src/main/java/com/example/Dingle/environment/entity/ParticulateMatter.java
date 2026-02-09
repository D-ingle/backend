package com.example.Dingle.environment.entity;

import com.example.Dingle.district.entity.District;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "particulate_matter")
public class ParticulateMatter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "pm10_concentration")
    private Integer pm10;

    @Column(name = "pm25_concentration")
    private Integer pm25;
}
