package com.example.Dingle.safety.entity;

import com.example.Dingle.district.entity.District;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "police_office")
public class PoliceOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    private String address;
    private double latitude;
    private double longitude;

    public PoliceOffice(District district, String address) {
        this.district = district;
        this.address = address;
    }
}
