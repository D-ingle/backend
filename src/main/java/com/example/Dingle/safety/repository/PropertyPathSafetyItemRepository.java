package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathSafetyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyPathSafetyItemRepository extends JpaRepository<PropertyPathSafetyItem, Long> {
    void deleteByPropertyId(Long propertyId);
}
