package com.example.Dingle.property.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.property.type.PropertyType;
import com.example.Dingle.realtor.entity.Realtor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "properties")
@Builder
public class Property {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realtor_id")
    private Realtor realtor;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    private String appartmentName;
    private String address;
    private Double latitude;
    private Double longitude;

    private Double exclusiveArea;
    private Double supplyArea;

    private Integer floor;
    private Integer totalFloor;
    private Integer bedrooms;
    private Integer bathrooms;
    private String orientation;

    private LocalDateTime registeredAt;
}
