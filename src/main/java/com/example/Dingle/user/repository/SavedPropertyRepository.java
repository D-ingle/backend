package com.example.Dingle.user.repository;

import com.example.Dingle.user.entity.SavedProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPropertyRepository extends JpaRepository<SavedProperty, Long> {
    boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
    Optional<SavedProperty> findByUserIdAndPropertyId(Long userId, Long propertyId);
    void deleteByUserIdAndPropertyId(Long userId, Long propertyId);
    List<SavedProperty> findAllByUserId(Long userId);
    List<SavedProperty> findAllByUserIdAndPropertyIdIn(Long userId, List<Long> propertyIds);
}
