package com.example.Dingle.safety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path_light")
public class PropertyPathLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_id", nullable = false)
    private Long pathId;

    @Column(name = "light_id", nullable = false)
    private Long lightId;

    public PropertyPathLight(Long pathId, Long lightId) {
        this.pathId = pathId;
        this.lightId = lightId;
    }
}
