package com.example.Dingle.safety.entity;

import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.property.entity.Property;
import com.example.Dingle.safety.type.PathSafetyItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "property_path_safety_item")
public class PropertyPathSafetyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PathSafetyItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crime_prone_area_id")
    private CrimeProneArea crimeProneArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_id")
    private Safety safety;

    public static PropertyPathSafetyItem crime(
            Property property,
            CrimeProneArea area
    ) {
        PropertyPathSafetyItem item = new PropertyPathSafetyItem();
        item.property = property;
        item.itemType = PathSafetyItemType.CRIME;
        item.crimeProneArea = area;
        return item;
    }

    public static PropertyPathSafetyItem safety(
            Property property,
            Safety safety
    ) {
        PropertyPathSafetyItem item = new PropertyPathSafetyItem();
        item.property = property;
        item.itemType = safety.getInfraType() == InfraType.CCTV
                ? PathSafetyItemType.CCTV
                : PathSafetyItemType.SAFETY_LIGHT;
        item.safety = safety;
        return item;
    }
}
