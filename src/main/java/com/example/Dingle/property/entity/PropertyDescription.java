package com.example.Dingle.property.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties_descriptions")
@Getter
@NoArgsConstructor
public class PropertyDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private int safetyDescription;
    private int environmentDescription;
    private int noiseDescription;
    private int convenienceDescription;
    private int accessibilityDescription;
}
