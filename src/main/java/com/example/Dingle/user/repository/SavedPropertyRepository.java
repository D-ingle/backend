package com.example.Dingle.user.repository;

import com.example.Dingle.user.entity.SavedProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPropertyRepository extends JpaRepository<SavedProperty, Long> {
    boolean existsByUserIdAndPropertyId(String userId, Long propertyId);
    Optional<SavedProperty> findByUserIdAndPropertyId(String userId, Long propertyId);
    void deleteByUserIdAndPropertyId(String userId, Long propertyId);
}
