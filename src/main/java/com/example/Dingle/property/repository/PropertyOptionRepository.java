package com.example.Dingle.property.repository;

import com.example.Dingle.property.entity.PropertyOption;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyOptionRepository extends JpaRepository<PropertyOption, Long> {
    @EntityGraph(attributePaths = "option")
    List<PropertyOption> findAllByPropertyId(Long propertyId);
}