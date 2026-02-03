package com.example.Dingle.district.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "districts")
@Getter
@NoArgsConstructor
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String districtName;

    @Column(nullable = false, length = 5)
    private String districtCode;

    public District(String name, String code) {
        this.districtName = name;
        this.districtCode = code;
    }
}
