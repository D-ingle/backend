package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyFacility;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyFacilityRepository extends JpaRepository<PropertyFacility, Long> {
    @EntityGraph(attributePaths = "facility")
    List<PropertyFacility> findAllByPropertyId(Long propertyId);
}
