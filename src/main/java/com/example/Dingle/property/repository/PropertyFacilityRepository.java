package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyFacilityRepository extends JpaRepository<PropertyFacility, Long> {
}
