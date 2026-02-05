package com.example.Dingle.infra.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.infra.type.Category;
import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.infra.type.SchoolType;
import com.example.Dingle.property.entity.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType schoolType;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private int distance;

}
