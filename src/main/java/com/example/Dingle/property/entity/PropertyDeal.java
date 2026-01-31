package com.example.Dingle.property.entity;

import com.example.Dingle.property.type.DealType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties_deals")
@NoArgsConstructor
@Getter
public class PropertyDeal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    private DealType dealType;

    private Long price;
    private Long deposit;
    private Long monthlyRent;

    public static PropertyDeal sale(Property property, Long price) {
        return new PropertyDeal(property, DealType.SALE, price, null, null);
    }

    public static PropertyDeal lease(Property property, Long price) {
        return new PropertyDeal(property, DealType.LEASE, price, null, null);
    }

    public static PropertyDeal rent(Property property, Long deposit, Long monthlyRent) {
        return new PropertyDeal(property, DealType.RENT, null, deposit, monthlyRent);
    }

    private PropertyDeal(Property property, DealType dealType, Long price, Long deposit, Long monthlyRent) {
        this.property = property;
        this.dealType = dealType;
        this.price = price;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
    }
}
