package com.example.Dingle.property.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.property.dto.OrientationType;
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

    private String apartmentName;
    private String address;
    private double latitude;
    private double longitude;

    private double exclusiveArea;
    private double supplyArea;

    private int floor;
    private int totalFloor;
    private int bedrooms;
    private int bathrooms;
    @Enumerated(EnumType.STRING)
    private OrientationType orientation;
    private Double parkingRatio;
    private Integer evParkingSpaces;

    private LocalDateTime registeredAt;

    @OneToOne(mappedBy = "property")
    private PropertyDeal deal;

    @OneToOne(mappedBy = "property")
    private PropertyScore score;
}
