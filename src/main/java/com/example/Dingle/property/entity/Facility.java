package com.example.Dingle.property.entity;

import com.example.Dingle.property.type.FacilityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facilities")
@Getter
@NoArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FacilityType facilityType;
}
