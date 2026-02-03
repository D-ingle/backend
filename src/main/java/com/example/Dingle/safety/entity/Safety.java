package com.example.Dingle.safety.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.infra.type.InfraType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "safety")
public class Safety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InfraType infraType;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public Safety(
            District district,
            InfraType infraType,
            Double latitude,
            Double longitude
    ) {
        this.district = district;
        this.infraType = infraType;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
