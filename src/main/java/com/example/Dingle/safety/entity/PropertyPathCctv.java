package com.example.Dingle.safety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path_cctv")
public class PropertyPathCctv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_id", nullable = false)
    private Long pathId;

    @Column(name = "cctv_id", nullable = false)
    private Long cctvId;

    public PropertyPathCctv(Long pathId, Long cctvId) {
        this.pathId = pathId;
        this.cctvId = cctvId;
    }
}
