package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyDealRepository extends JpaRepository<PropertyDeal, Long> {
}
