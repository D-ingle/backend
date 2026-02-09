package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathSafetyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyPathSafetyItemRepository extends JpaRepository<PropertyPathSafetyItem, Long> {
    void deleteByPropertyId(Long propertyId);
    Optional<PropertyPathSafetyItem> findByProperty_Id(Long propertyId);
}
