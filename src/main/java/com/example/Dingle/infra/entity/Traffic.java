package com.example.Dingle.infra.entity;

import com.example.Dingle.infra.type.SchoolType;
import com.example.Dingle.infra.type.TrafficType;
import com.example.Dingle.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "traffic")
public class Traffic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrafficType trafficType;

    @Column(nullable = false)
    private String trafficName;

    private String busNumber;

    @Column(nullable = false)
    private int timeTaken;
}
